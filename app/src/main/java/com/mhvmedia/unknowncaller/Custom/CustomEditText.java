package com.mhvmedia.unknowncaller.Custom;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
/** Created by AwsmCreators * */
public class CustomEditText extends AppCompatEditText {
    private boolean isNeedNoChangeSomeCharacters;
    private String charactersNoChange;

    public CustomEditText(Context context) {
        super(context);
        init(context, null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, @Nullable AttributeSet attrs) {

        if (isInEditMode())
            return;

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isNeedNoChangeSomeCharacters && charactersNoChange != null) {
                    if (!getText().toString().startsWith(charactersNoChange)) {
                        removeTextChangedListener(this);
                        if (charactersNoChange.length() > s.length()) {
                            setText(charactersNoChange);
                        } else {
                            setText(charactersNoChange + getText());
                        }
                        setSelection(getText().toString().length());
                        addTextChangedListener(this);
                    }
                }
            }
        });

    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        if (isNeedNoChangeSomeCharacters && charactersNoChange != null) {
            if (length() > charactersNoChange.length() && selStart < charactersNoChange.length()) {
                setSelection(charactersNoChange.length(),selEnd);
            }
        }
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (isNeedNoChangeSomeCharacters && charactersNoChange != null) {
            if (!getText().toString().trim().startsWith(charactersNoChange)) {
                setText(charactersNoChange + getText());
            }
        }
    }

    public void setCharactersNoChangeInitial(String charactersNoChange) {
        isNeedNoChangeSomeCharacters = true;
        this.charactersNoChange = charactersNoChange;
        if (!getText().toString().trim().startsWith(charactersNoChange)) {
            setText(getText());
        }
    }

}
