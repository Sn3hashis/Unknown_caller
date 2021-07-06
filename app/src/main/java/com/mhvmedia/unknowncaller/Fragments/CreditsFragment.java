package com.mhvmedia.unknowncaller.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mhvmedia.unknowncaller.Activities.MainActivity;
import com.mhvmedia.unknowncaller.Activities.ReferActivity;
import com.mhvmedia.unknowncaller.Adapters.SkuAdapter;
import com.mhvmedia.unknowncaller.Model.User;
import com.mhvmedia.unknowncaller.R;
import com.mhvmedia.unknowncaller.Variables.Variables;
import com.mhvmedia.unknowncaller.nativetemplates.NativeTemplateStyle;
import com.mhvmedia.unknowncaller.nativetemplates.TemplateView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

/** Created by AwsmCreators * */
public class CreditsFragment extends Fragment implements SkuAdapter.OnItemClickListener, PurchasesUpdatedListener {
    private ImageButton play_video, refer_friend;
    private String single_choice_selected;
    private static String[] PAYMENT_CHOICE = new String[]{
            "Google play", "UPI(Google pay)"
    };
    private TextView credits, refer_description;
    private RewardedAd rewardedAd;
    Dialog dialog;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private Double previousPoints;
    private Integer payCredits;
    private final int UPI_PAYMENT = 0;
    private Dialog paymentDialog;
    String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    int GOOGLE_PAY_REQUEST_CODE = 123;

    //dialog
    private EditText noteEt, nameEt, upiIdEt;
    private TextView packName, packCost, packCredits;
    private Button send;
    private ImageButton closeDialog;

    private LinearLayout paymentLayout, sucessLayout;
    private Button closeSucessDialog;
    private boolean readyToPurchase = false;
    static final String TAG = MainActivity.class.getSimpleName();

    //For inapp purchase
    private List<SkuDetails> mPaymentProductModels;
    private SkuAdapter mPaymentProductListAdapter;
    public RecyclerView mRecyclerView;
    private SkuDetails productModel;
    private BillingClient mBillingClient;
    ProgressDialog progressDialog;


    public CreditsFragment() {
        // Required empty public constructor
    }
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_credits,container,false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        RetriveData();

        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable
                (Color.TRANSPARENT));
        dialog.setCancelable(false);
        rewardedAd = new RewardedAd(getActivity(), getResources().getString(R.string.admob_rewarded_video));

        credits = view.findViewById(R.id.credits);
        refer_description = view.findViewById(R.id.refer_description);
        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    refer_description.setText("Get "+Variables.REFERRAL_POINTS.intValue()+" points for every friend you invite");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        play_video = view.findViewById(R.id.play_video);
        refer_friend = view.findViewById(R.id.refer_friend);
        if (!Variables.ENABLE_UPI){
            PAYMENT_CHOICE = new String[]{
                    "Google play"
            };
        }

        //inapp purchase
        mPaymentProductModels = new ArrayList<>();
        mPaymentProductListAdapter = new SkuAdapter(mPaymentProductModels, this);
        mRecyclerView = view.findViewById(R.id.productList_productPackageList);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("adding_credits_to_wallet");
        progressDialog.setCancelable(false);
        initComponent();

        LoadNativeAds();
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback(){
            @Override
            public void onRewardedAdLoaded() {
                super.onRewardedAdLoaded();
            }

            @Override
            public void onRewardedAdFailedToLoad(int i) {
                super.onRewardedAdFailedToLoad(i);
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);

        play_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showRewardedVideo();
                    }
                }, 4000);
            }
        });

        if (Variables.ENABLE_REFERRAL){
            refer_friend.setVisibility(View.VISIBLE);
            refer_friend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), ReferActivity.class));
                }
            });
        }else {
            refer_friend.setVisibility(View.GONE);
        }

        return view;
    }

    private void initComponent() {
        mRecyclerView.setAdapter(mPaymentProductListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(false);
        mRecyclerView.setItemViewCacheSize(12);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setBackgroundResource(R.color.white);
        mRecyclerView.setBackgroundColor(Color.WHITE);
        mRecyclerView.setLayoutManager(layoutManager);
        initPurchase();
    }

    private void initPurchase() {
        mBillingClient = BillingClient.newBuilder(getActivity()).setListener(this).enablePendingPurchases().build();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    initSKUCredits();
                }
            }
            @Override
            public void onBillingServiceDisconnected() {
            }
        });
    }

    private void initSKUCredits() {
        List<String> creditsPurchase = new ArrayList<> ();
        creditsPurchase.add(Variables.SMALL_PACK_ID);
        creditsPurchase.add(Variables.MEDIUM_PACK_ID);
        creditsPurchase.add(Variables.BIG_PACK_ID);

        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(creditsPurchase).setType(BillingClient.SkuType.INAPP);
        mBillingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                        // Process the result.

                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                            if (skuDetailsList.size() >0){
                                createList(skuDetailsList, skuDetailsList.get(0));
                            }else {
                            }

                        } else {
                        }
                    }
                });
    }

    private void createList(List<SkuDetails> skuDetailsList, SkuDetails skuDetails) {
        mPaymentProductModels.clear();
        mPaymentProductModels.addAll(skuDetailsList);
        mPaymentProductListAdapter.notifyDataSetChanged();
        productModel = mPaymentProductModels.get(0);
    }
    public void initPurchaseFlow(){
        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(productModel)
                .build();
        mBillingClient.launchBillingFlow(getActivity(), flowParams);
    }
    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                && purchases != null) {
            for (Purchase purchase : purchases) {
                if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                    switch (purchase.getSku()) {
                        case Variables.SMALL_PACK_ID:
                            acknowledgePurchaseConsume(Variables.SMALL_PACK_CREDITS, purchase);
                            break;
                        case Variables.MEDIUM_PACK_ID:
                            acknowledgePurchaseConsume(Variables.MEDIUM_PACK_CREDITS, purchase);
                            break;
                        case Variables.BIG_PACK_ID:
                            acknowledgePurchaseConsume(Variables.BIG_PACK_CREDITS, purchase);
                            break;
                    }
                }
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            Toast.makeText(getActivity(), "purchase canceled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "error_try_again_later", Toast.LENGTH_SHORT).show();
        }
    }
    public void acknowledgePurchaseConsume(final int credits, Purchase purchase){
        ConsumeParams consumeParams = ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.getPurchaseToken())
                .build();
        ConsumeResponseListener consumeResponseListener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                RewardUser(credits);
            }
        };
        mBillingClient.consumeAsync(consumeParams, consumeResponseListener);
    }
    @Override
    public void onItemClick(SkuDetails item) {
        mPaymentProductListAdapter.notifyDataSetChanged();
        productModel = item;
        if (item.getSku().equals(Variables.SMALL_PACK_ID)){
            payCredits = Variables.SMALL_PACK_CREDITS;
            single_choice_selected = PAYMENT_CHOICE[0];
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Choose payment");
            builder.setSingleChoiceItems(PAYMENT_CHOICE, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    single_choice_selected = PAYMENT_CHOICE[i];
                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    if (single_choice_selected.equals("Google play")) {
                        initPurchaseFlow();

                    } else if (single_choice_selected.equals("UPI(Google pay)")) {
                        showUpiDialog(Variables.SMALL_PACK_NAME,Variables.SMALL_PACK_COST, payCredits);
                    }

                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        }else if (item.getSku().equals(Variables.MEDIUM_PACK_ID)){
            payCredits = Variables.MEDIUM_PACK_CREDITS;
            single_choice_selected = PAYMENT_CHOICE[0];
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Choose payment");
            builder.setSingleChoiceItems(PAYMENT_CHOICE, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    single_choice_selected = PAYMENT_CHOICE[i];
                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    if (single_choice_selected.equals("Google play")) {
                        initPurchaseFlow();

                    } else if (single_choice_selected.equals("UPI(Google pay)")) {
                        showUpiDialog(Variables.MEDIUM_PACK_NAME, Variables.MEDIUM_PACK_COST, payCredits);
                    }

                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();

        }else if (item.getSku().equals(Variables.BIG_PACK_ID)){
            payCredits = Variables.BIG_PACK_CREDITS;
            single_choice_selected = PAYMENT_CHOICE[0];
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Choose payment");
            builder.setSingleChoiceItems(PAYMENT_CHOICE, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    single_choice_selected = PAYMENT_CHOICE[i];
                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    if (single_choice_selected.equals("Google play")) {
                        initPurchaseFlow();

                    } else if (single_choice_selected.equals("UPI(Google pay)")) {
                        showUpiDialog(Variables.BIG_PACK_NAME, Variables.BIG_PACK_COST, payCredits);
                    }

                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        }
    }
    private void RetriveData() {
        mDatabase.child("users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    User user = dataSnapshot.getValue(User.class);
                    int totalPoints = user.getCredits().intValue();
                    credits.setText("My Credits : "+Integer.toString(totalPoints));
                    previousPoints = user.getCredits();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void showRewardedVideo() {
        //dialog.show();
        if (rewardedAd.isLoaded()) {
            Activity activityContext = getActivity();
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                    // Ad opened.
                }

                @Override
                public void onRewardedAdClosed() {
                    rewardedAd = createAndLoadRewardedAd();
                    dialog.dismiss();
                    // Ad closed.
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem reward) {
                    Toast.makeText(getActivity(), "You got 5+ credits", Toast.LENGTH_SHORT).show();
                    VideoReward();
                    // User earned reward.
                }

                @Override
                public void onRewardedAdFailedToShow(int errorCode) {
                    dialog.dismiss();
                    // Ad failed to display.
                }
            };
            rewardedAd.show(activityContext, adCallback);
        } else {
            dialog.dismiss();
            Toast.makeText(getActivity(), "Video is not ready , try again later", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
        }
    }
    private void VideoReward() {
        Random random = new Random();
        int lowRange = 1;
        int highRange = 5;
        int result = random.nextInt(highRange - lowRange) + lowRange;
        RewardUser(result);
    }
    private void RewardUser(int credits) {
        Double totalpoints = previousPoints+=Double.valueOf(credits);
        mDatabase.child("users").child(currentUser.getUid()).child("credits").setValue(totalpoints);
        Toast.makeText(getActivity(), "+"+credits+" "+"Credits Added to your wallet", Toast.LENGTH_SHORT).show();
    }
    private void LoadNativeAds() {
        AdLoader adLoader = new AdLoader.Builder(getActivity(), getResources().getString(R.string.admob_native))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().build();

                        TemplateView template = view.findViewById(R.id.native_ad);
                        template.setStyles(styles);
                        template.setNativeAd(unifiedNativeAd);

                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());

    }

    public RewardedAd createAndLoadRewardedAd() {
        RewardedAd rewardedAd = new RewardedAd(getActivity(), getResources().getString(R.string.admob_rewarded_video));
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        return rewardedAd;
    }
    private void showUpiDialog(String packagename, final Integer packagecost, Integer packagecredits) {
        paymentDialog = new Dialog(getActivity());
        paymentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        paymentDialog.setContentView(R.layout.payment_dialog);
        paymentDialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(paymentDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        paymentLayout = paymentDialog.findViewById(R.id.payment_layout);
        sucessLayout = paymentDialog.findViewById(R.id.sucess_layout);
        closeSucessDialog = paymentDialog.findViewById(R.id.close_dialog);

        send = paymentDialog.findViewById(R.id.send);
        noteEt = paymentDialog.findViewById(R.id.note);
        nameEt = paymentDialog.findViewById(R.id.name);
        upiIdEt = paymentDialog.findViewById(R.id.upi_id);
        closeDialog = paymentDialog.findViewById(R.id.btn_close);
        packName = paymentDialog.findViewById(R.id.pack_name);
        packCost = paymentDialog.findViewById(R.id.pack_cost);
        packCredits = paymentDialog.findViewById(R.id.pack_credits);

        packName.setText(packagename);
        packCost.setText("₹"+packagecost);
        packCredits.setText(packagecredits+" "+"Credits");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pkgCost = Integer.toString(packagecost);
                String note = noteEt.getText().toString();
                String name = nameEt.getText().toString();
                String upiId = upiIdEt.getText().toString();
                payUsingUpi(pkgCost, Variables.UPI_ID, Variables.UPI_NAME, note);
            }
        });

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentDialog.dismiss();
            }
        });

        closeSucessDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentDialog.dismiss();
            }
        });

        paymentDialog.show();
        paymentDialog.getWindow().setAttributes(lp);
    }
    private void payUsingUpi(String amount, String upiId, String name, String note) {
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();

        PackageManager packageManager = getActivity().getPackageManager();
        if (isGooglePayInstalled(GOOGLE_PAY_PACKAGE_NAME,packageManager)){
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
                startActivityForResult(intent, UPI_PAYMENT);
            } catch (Exception error) {
                Toast.makeText(getActivity(), "Please open google pay first and login with upi details and try again", Toast.LENGTH_SHORT).show();
            }
        }else {
            Uri marketUri = Uri.parse("market://details?id=" + GOOGLE_PAY_PACKAGE_NAME);
            Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
            startActivity(marketIntent);
            Toast.makeText(getActivity(), "Please install google pay (tez)", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(getActivity())) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                RewardUser(payCredits);
                paymentLayout.setVisibility(View.GONE);
                sucessLayout.setVisibility(View.VISIBLE);
                //Code to handle successful transaction here.
                Toast.makeText(getActivity(), "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(getActivity(), "Payment cancelled by user or upi not set up.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(), "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private boolean isGooglePayInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}