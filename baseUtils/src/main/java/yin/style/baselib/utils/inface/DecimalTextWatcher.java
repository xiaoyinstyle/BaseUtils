package yin.style.baselib.utils.inface;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Author by ChneYin, Email 976370887@qq.com, Date on  2018/5/18.
 * 输入带小数点的 TextWatcher
 */
public abstract class DecimalTextWatcher implements TextWatcher {
    private int digits = 2;//默认两位小数
    private EditText editText;

    public DecimalTextWatcher(EditText editText) {
        this.editText = editText;
    }

    public DecimalTextWatcher(EditText editText, int decimalNum) {
        this.digits = decimalNum;
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //删除“.”后面超过2位后的数据
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > digits) {
                s = s.toString().subSequence(0, s.toString().indexOf(".") + digits + 1);
                editText.setText(s);
                editText.setSelection(s.length()); //光标移到最后
            }
        }
        //如果"."在起始位置,则起始位置自动补0
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            editText.setText(s);
            editText.setSelection(2);
        }

        //如果起始位置为0,且第二位跟的不是".",则删除前面的 0
        if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                editText.setText(s.subSequence(1, s.length()));
                editText.setSelection(s.length() - 1);
                return;
            }
        }

        //没有内容是则为0
        if (s == null || s.length() == 0) {
            editText.setText("0");
            editText.setSelection(1);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

        inputText(TextUtils.isEmpty(editable.toString()) ? "0" : editable.toString());
    }

    public abstract void inputText(String number);
}
