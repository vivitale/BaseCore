package talex.zsw.basecore.util.glide;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.target.ViewTarget;

import java.io.InputStream;

import talex.zsw.basecore.R;
import talex.zsw.basecore.util.FileTool;

/**
 * 作用: Glide基础配置
 * 作者: 赵小白 email:vvtale@gmail.com  
 * 日期: 2018/5/30 01:59 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
@GlideModule
public class CustomGlideModule extends AppGlideModule
{
	@Override public void applyOptions(Context context, GlideBuilder builder)
	{
		ViewTarget.setTagId(R.id.glide_tag_id);

		// 设置内存缓存
		MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
		int memoryCacheSizeBytes = 1024*1024*20; // 20mb
		int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
		int customBitmapPoolSize = (int) (1.2*defaultBitmapPoolSize);
		builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));
		builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

		// 设置磁盘缓存
		int cacheSize100MegaBytes = 200*1024*1024;// 200M

		//  根据SD卡是否可用选择是在内部缓存还是SD卡缓存
		if(FileTool.isSDCardEnable())
		{
			builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, "HYManagerImages", cacheSize100MegaBytes));//缓存到外部存储
		}
		else
		{
			builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "HYManagerImages", cacheSize100MegaBytes));//缓存到应用的内部目录
		}
	}

	/**
	 * 禁用清单解析
	 * 这样可以改善 Glide 的初始启动时间，并避免尝试解析元数据时的一些潜在问题。
	 */
	@Override public boolean isManifestParsingEnabled()
	{
		return false;
	}

	/**
	 * 使用OkHttp加载
	 */
	@Override public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry)
	{
		registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
	}
}
