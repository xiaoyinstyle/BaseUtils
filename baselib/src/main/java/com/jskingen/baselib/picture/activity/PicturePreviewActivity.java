package com.jskingen.baselib.picture.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jskingen.baselib.R;
import com.jskingen.baselib.activity.base.NormalAcitivity;
import com.jskingen.baselib.fragment.base.BaseImageFragment;
import com.jskingen.baselib.fragment.inter.OnPageChangeListener;
import com.jskingen.baselib.picture.model.MediaFile;
import com.jskingen.baselib.picture.utils.AnimationUtils;
import com.jskingen.baselib.picture.utils.PictureManage;
import com.jskingen.baselib.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jskingen.baselib.picture.fragment.PictureAlbumFragment.PICTURE_TAG_COMPLETE;

public class PicturePreviewActivity extends NormalAcitivity {
    private static final String IMAGELIST = "IMAGELIST";
    private ImageView ivLeft;
    private CheckBox cbRight;
    private TextView tvTitle;
    private TextView picSelNumb;
    private TextView picSelComplete;

    private BaseImageFragment fragment;
    private int currentPos;
    private List<MediaFile> list = new ArrayList<>();

    private int selNum = 0;

    public static void start(Context context, int currentPos) {
        Intent intent = new Intent(context, PicturePreviewActivity.class);
        intent.putExtra(IMAGELIST, currentPos);
        context.startActivity(intent);
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_picture_preview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ivLeft = (ImageView) findViewById(R.id.iv_left);
        cbRight = (CheckBox) findViewById(R.id.cb_right);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        picSelNumb = (TextView) findViewById(R.id.pic_sel_numb);
        picSelComplete = (TextView) findViewById(R.id.pic_sel_complete);

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cbRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //超过最多选择
                if (cbRight.isChecked() && !PictureManage.getInstance().canAdd()) {
                    cbRight.setChecked(false);
                    return;
                }

                if (cbRight.isChecked()) {
                    AnimationUtils.start(cbRight);
                    PictureManage.getInstance().addFile(list.get(currentPos));

                    selNum++;
                } else {
                    PictureManage.getInstance().removeFile(list.get(currentPos));
                    selNum--;
                }
                setView(selNum);
            }
        });

        picSelComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getInstance().post(PICTURE_TAG_COMPLETE);
                finish();
            }
        });


        fragment = new BaseImageFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fm_content, fragment).commit();


        fragment.setListener(new OnPageChangeListener() {
            @Override
            public void onChange(int current, int all) {
                tvTitle.setText((current + 1) + "/" + all);
                currentPos = current;
                if (list.size() != 0)
                    cbRight.setChecked(list.get(currentPos).isChecked());
            }
        });
    }

    @Override
    protected void initData() {
        this.currentPos = getIntent().getIntExtra(IMAGELIST, -1);
        int page = PictureManage.getInstance().position;

        list.clear();
        if (currentPos < 0) {
            list.addAll(PictureManage.getInstance().getSelectList());
            selNum = list.size();
        } else {
            list.addAll(PictureManage.getInstance().getMediaFolders().get(page).getImageFiles());
            selNum = 0;
            for (MediaFile mediaFile : list)
                if (mediaFile.isChecked())
                    selNum++;
        }
        setView(selNum);

        final List<String> tlist = new ArrayList<>();
        for (MediaFile mediaFile : list) {
            tlist.add(mediaFile.getPath());
        }
        fragment.setData(tlist, currentPos < 0 ? 0 : currentPos);
    }


    private void setView(int selNum) {
        picSelNumb.setText(selNum + "");
        if (selNum < 1) {
            picSelComplete.setEnabled(false);
            picSelNumb.setEnabled(false);
            picSelNumb.setVisibility(View.GONE);
            picSelComplete.setText(R.string.pic_choice_null);
        } else {
            picSelComplete.setEnabled(true);
            picSelNumb.setEnabled(true);
            picSelNumb.setVisibility(View.VISIBLE);
            picSelComplete.setText(R.string.pic_choice_has);
        }
    }

    @Override
    protected boolean removeAppManager() {
        return true;
    }
}
