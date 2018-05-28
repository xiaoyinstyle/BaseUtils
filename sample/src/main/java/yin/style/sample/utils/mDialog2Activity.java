package yin.style.sample.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.activity.view.TitleLayout;
import yin.style.baselib.utils.ToastUtils;
import yin.style.baselib.view.IOSDialog;
import yin.style.sample.R;


import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class mDialog2Activity extends TitleActivity implements OnClickListener {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;

    @Override
    protected int getViewByXml() {
        return R.layout.activity_m_dialog2;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    private void showAlerDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("AlertDialog")
                .setMessage("这是一个AlertDialog")
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", null)
                .create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                showAlerDialog();
                break;
            case R.id.btn2:
                new IOSDialog(mContext)
                        .setTitle("提示", getResources().getColor(R.color.text_black), true)
                        .setMessage("这是文字", getResources().getColor(R.color.text_black), false)
                        .setPositiveButton("Positive", new IOSDialog.OnClickListener() {
                            @Override
                            public boolean onClick(Dialog dialog, View view) {
                                ToastUtils.show("Positive");
                                return false;
                            }
                        })
                        .setNegativeButton("Negative", Color.RED, null)
                        .show();
                break;
            case R.id.btn3:

                new IOSDialog(mContext)
                        .setTitle("提示", getResources().getColor(R.color.text_black), true)
                        .setContentView(R.layout.dialog_test, new IOSDialog.ViewListener() {
                            @Override
                            public void viewHold(View view) {
                                TextView textView = view.findViewById(R.id.tv_1);
                                textView.setText("自定义View,点击按钮退出");
                                view.findViewById(R.id.tv_2).setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        ToastUtils.show("点击");
                                        finish();
                                    }
                                });
                            }
                        })
                        .setPositiveButton("Positive", new IOSDialog.OnClickListener() {
                            @Override
                            public boolean onClick(Dialog dialog, View view) {
                                ToastUtils.show("Positive");
                                return false;
                            }
                        })
                        .setNegativeButton("Negative", Color.RED, new IOSDialog.OnClickListener() {
                            @Override
                            public boolean onClick(Dialog dialog, View view) {
                                ToastUtils.show("Negative");
                                return true;
                            }
                        })
                        .show();
                break;
            case R.id.btn4:
                new IOSDialog(mContext)
//                        .setTitle("提示", getResources().getColor(R.color.text_black), true)
                        .setContentView(R.layout.dialog_test, new IOSDialog.ViewListener() {
                            @Override
                            public void viewHold(View view) {
                                TextView textView = view.findViewById(R.id.tv_1);
                                textView.setText("自定义View,点击按钮退出");
                                view.findViewById(R.id.tv_2).setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        ToastUtils.show("点击");
                                        finish();
                                    }
                                });
                            }
                        })
                        .setPositiveButton("Positive", new IOSDialog.OnClickListener() {
                            @Override
                            public boolean onClick(Dialog dialog, View view) {
                                ToastUtils.show("Positive");
                                return false;
                            }
                        })
                        .setNegativeButton("Negative", Color.RED, new IOSDialog.OnClickListener() {
                            @Override
                            public boolean onClick(Dialog dialog, View view) {
                                ToastUtils.show("Negative");
                                return true;
                            }
                        })
                        .show();
                break;
            case R.id.btn5:

                break;
            default:
                break;
        }
    }
    @Override
    protected void setTitle(TitleLayout titleLayout) {
        title.setText("仿IOS Dialog");
    }
}
