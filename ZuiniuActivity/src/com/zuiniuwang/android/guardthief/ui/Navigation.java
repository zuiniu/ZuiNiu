package com.zuiniuwang.android.guardthief.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.viewpagerindicator.IconPageIndicator;
import com.viewpagerindicator.IconPagerAdapter;
import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.util.AwakeningUtil;
import com.zuiniuwang.android.guardthief.util.CustomConfiguration;
import com.zuiniuwang.android.guardthief.util.NavigationUtil;
import com.zuiniuwang.android.guardthief.util.UiUtil;

/**
 * 主页面导航页面
 * 
 * @author guoruiliang
 * 
 */
public class Navigation extends FragmentActivity {

	private Context mContext;

	private GestureDetector gestureDetector; // 用户滑动
	private int currentItem = 0; // 当前图片的索引号
	private int flaggingWidth;// 互动翻页所需滚动的长度是当前屏幕宽度的1/3

	MainFragmentAdapter mAdapter;
	ViewPager mPager;
	IconPageIndicator mIndicator;

	
	/***这导航页面是不需要验证的，所以直接写死*/
	@Override
	protected void onStart() {
		super.onStart();
		AwakeningUtil.gotoStartState();

	}

	protected static final int[] images = new int[] { R.drawable.lead1,
			R.drawable.lead2, R.drawable.lead3, R.drawable.lead4,
			R.drawable.lead5 };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mContext = Navigation.this;
		setContentView(R.layout.guard_second_navigation);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // 全屏显示

		mAdapter = new MainFragmentAdapter(getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		mIndicator = (IconPageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
		/** 手势 */
		gestureDetector = new GestureDetector(new GuideViewTouch());
		// 获取分辨率
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		flaggingWidth = dm.widthPixels / 6;

		mIndicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				currentItem = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		// 魅族手机隐藏menu
		UiUtil.hideMeizuMenu(mPager);
	}

	/**
	 * 导航适配
	 * 
	 * @author guoruiliang
	 * 
	 */
	static class MainFragmentAdapter extends FragmentPagerAdapter implements
			IconPagerAdapter {

		protected static final int[] ICONS = new int[] {
				R.drawable.selector_circle, R.drawable.selector_circle,
				R.drawable.selector_circle, R.drawable.selector_circle,
				R.drawable.selector_circle };

		private int mCount = images.length;

		public MainFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			GuardFragment fragment = GuardFragment
					.newInstance(images[position]);
			// 最后一行显示按钮
			if (position == images.length - 1) {
				fragment.nextVisible = true;
			}

			return fragment;

		}

		@Override
		public int getCount() {
			return mCount;
		}

		@Override
		public int getIconResId(int index) {
			return ICONS[index % ICONS.length];
		}

		/** 导航fragment */
		public static class GuardFragment extends Fragment {
			private static final String KEY_CONTENT = "TestFragment:Content";

			private ImageButton nextButton;

			private boolean nextVisible = false;

			public static GuardFragment newInstance(int imageId) {
				GuardFragment fragment = new GuardFragment();
				fragment.imageId = imageId;
				return fragment;
			}

			private int imageId = 0;

			@Override
			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);

				if ((savedInstanceState != null)
						&& savedInstanceState.containsKey(KEY_CONTENT)) {
					imageId = savedInstanceState.getInt(KEY_CONTENT);
				}
			}

			@Override
			public View onCreateView(LayoutInflater inflater,
					ViewGroup container, Bundle savedInstanceState) {

				View view = LayoutInflater.from(getActivity()).inflate(
						R.layout.navigation, null);
				ImageView image = (ImageView) view.findViewById(R.id.image);
				image.setImageResource(imageId);
				nextButton = (ImageButton) view.findViewById(R.id.next);
				nextButton
						.setVisibility(nextVisible ? View.VISIBLE : View.GONE);

				nextButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoMain(getActivity());
					}
				});

				return view;
			}

			@Override
			public void onSaveInstanceState(Bundle outState) {
				super.onSaveInstanceState(outState);
				outState.putInt(KEY_CONTENT, imageId);
			}
		}
	}

	/** 右滑，同样进入主页面 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);
		return super.dispatchTouchEvent(event);
	}

	private static void gotoMain(Activity activity) {
		CustomConfiguration.setIsNavigationed(true);// 设置导航完毕
		NavigationUtil.gotoMainPage(activity, false);
		activity.finish();
		activity.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);

		// if (CustomConfiguration.isAllSetted())
		// activity.overridePendingTransition(R.anim.push_right_in,
		// R.anim.push_right_out);
		// else
		// activity.overridePendingTransition(R.anim.push_left_in,
		// R.anim.push_left_out);

	}

	/** 手势，判断当最后一页的时候滑动跳转到主页面 */
	private class GuideViewTouch extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {

			// /** 最后一页滑动 */
			// if (currentItem == images.length - 1) {
			// if (Math.abs(e1.getX() - e2.getX()) > Math.abs(e1.getY()
			// - e2.getY())
			// && (e1.getX() - e2.getX() <= (-flaggingWidth) || e1
			// .getX() - e2.getX() >= flaggingWidth)) {
			// if (e1.getX() - e2.getX() >= flaggingWidth) {
			// gotoMain((Activity) mContext);
			// return true;
			// }
			// }
			// }
			return false;
		}
	}

	/** 点回退键，和完成导航一样的效果 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			gotoMain((Activity) mContext);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
