package com.donkingliang.imageselector;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.donkingliang.imageselector.utils.ImageUtil;
import com.donkingliang.imageselector.utils.StringUtils;
import com.donkingliang.imageselector.view.ClipImageView;

import java.io.File;
import java.util.ArrayList;

import static com.donkingliang.imageselector.utils.ImageUtil.AVATAR_CROP_FILE;
import static com.donkingliang.imageselector.utils.ImageUtil.AVATAR_FILE;
import static com.donkingliang.imageselector.utils.ImageUtil.CAMERA_WITH_DATA;
import static com.donkingliang.imageselector.utils.ImageUtil.IMAGE_FILE;
import static com.donkingliang.imageselector.utils.ImageUtil.PHOTO_CROP_RESOULT;

public class ClipImageActivity extends Activity {

    private FrameLayout btnConfirm;
    private FrameLayout btnBack;
    private ClipImageView imageView;
    private int mRequestCode;
    public static final int SELECT_IMAGE = 9000;
    public static final int TAKE_PHOTO = 9001;

    /**
     * 拍照的 Uri
     */
    private static Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_clip_image);
        mRequestCode = getIntent().getIntExtra("requestCode", 0);
        if (getIntent().getBooleanExtra("camera",false)){
            mImageUri = ImageUtil.doTakePhoto(this);
        }else{
            setStatusBarColor();
            ImageSelectorUtils.openPhoto(this, mRequestCode, true, 0);
            initView();
        }

    }

    /**
     * 修改状态栏颜色
     */
    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#373c3d"));
        }
    }

    private void initView() {
        imageView = (ClipImageView) findViewById(R.id.process_img);
        btnConfirm = (FrameLayout) findViewById(R.id.btn_confirm);
        btnBack = (FrameLayout) findViewById(R.id.btn_back);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.getDrawable() != null) {
                    btnConfirm.setEnabled(false);
                    confirm(imageView.clipImage());
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == RESULT_OK){
            if (requestCode == SELECT_IMAGE){
                ArrayList<String> images = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
                Bitmap bitmap = ImageUtil.decodeSampledBitmapFromFile(images.get(0), 720, 1080);
                if (bitmap != null) {
                    imageView.setBitmapData(bitmap);
                } else {
                    finish();
                }
            }else if (requestCode == CAMERA_WITH_DATA){
                ImageUtil.startPhotoZoom(this,ImageUtil.getImageUri(this,mImageUri,AVATAR_FILE));
            }else if (requestCode == PHOTO_CROP_RESOULT){
                Intent intent = new Intent();
                intent.putExtra(ImageSelectorUtils.SELECT_RESULT,IMAGE_FILE+ "/" + AVATAR_CROP_FILE);
                setResult(RESULT_OK,intent);
                finish();
            }else{
                finish();
            }

        }else{
            finish();
        }

    }

    private void confirm(Bitmap bitmap) {
        String imagePath = null;
        if (bitmap != null) {
            imagePath = ImageUtil.saveImage(bitmap, getCacheDir().getPath() + File.separator + "image_select");
            bitmap.recycle();
            bitmap = null;
        }

        if (StringUtils.isNotEmptyString(imagePath)) {
            ArrayList<String> selectImages = new ArrayList<>();
            selectImages.add(imagePath);
            Intent intent = new Intent();
            intent.putStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT, selectImages);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    public static void openActivity(Activity context, int requestCode) {
        Intent intent = new Intent(context, ClipImageActivity.class);
        intent.putExtra("requestCode", requestCode);
        context.startActivityForResult(intent, requestCode);
    }

    public static void openCamera(Activity context, int requestCode){
        Intent intent = new Intent(context, ClipImageActivity.class);
        intent.putExtra("camera", true);
        intent.putExtra("requestCode", requestCode);
        context.startActivityForResult(intent, requestCode);
    }

}
