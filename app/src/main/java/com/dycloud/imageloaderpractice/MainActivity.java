package com.dycloud.imageloaderpractice;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager bannerPager;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private List<String> imgList = new ArrayList<String>();
    private final String []imgUrl = {"drawable://" + R.drawable.banner01,
                                "drawable://" + R.drawable.banner02,
                                "drawable://" + R.drawable.banner03 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        configImageLoader();
        initEvent();

    }

    private void initEvent() {
        initList();
        MyBannerAdapter myBannerAdapter = new MyBannerAdapter(imgList, this);
        bannerPager.setAdapter(myBannerAdapter);
    }

    private void initList() {
        for(int i = 0; i < imgUrl.length; i++) {
            imgList.add(imgUrl[i]);
        }
    }

    private void initView() {
        bannerPager = (ViewPager) findViewById(R.id.id_banner_viewpager);
    }

    class MyBannerAdapter extends PagerAdapter {
        private List<String> mList;
        private Context mContext;
        public MyBannerAdapter(List<String> mList, Context mContext) {
            this.mList = mList;
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View imageLayout = LayoutInflater.from(mContext).inflate(R.layout.layout_image, null);
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.id_img_for_loader);
            imageLoader.displayImage(mList.get(position), imageView);
            ((ViewPager) container).addView(imageLayout);
            return imageLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }

    private void configImageLoader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();
        imageLoader.init(configuration);
    }
}
