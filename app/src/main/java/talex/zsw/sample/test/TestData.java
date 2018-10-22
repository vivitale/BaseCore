package talex.zsw.sample.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static butterknife.internal.Utils.listOf;

/**
 * 作用：测试数据
 * 作者：赵小白 email:vvtale@gmail.com  
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TestData
{
	public static List<String> getRoomImages()
	{
		List<String> list = new ArrayList<>();
		list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512332524350&di=6cc20a153ec70362133498bc24d9efb7&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fa1ec08fa513d2697d4e7a6b65ffbb2fb4316d821.jpg");
		list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512332524347&di=a92b3e339c1af0e8293ab54a0af0a522&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F738b4710b912c8fc214c1e23f6039245d78821a5.jpg");
		list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512332524344&di=965a80392810be012a20b7c50fae6e31&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F08f790529822720eda22881671cb0a46f31fab6a.jpg");
		list.add("http://img1.imgtn.bdimg.com/it/u=1096745206,3964147381&fm=27&gp=0.jpg");
		list.add("https://img1.gtimg.com/huainan_house/pics/hv1/2/33/93/6055742.jpg");
		list.add("http://imgsrc.baidu.com/imgad/pic/item/94cad1c8a786c9170e4f27a4c33d70cf3ac757da.jpg");
		list.add("http://pic.58pic.com/58pic/16/03/06/95G58PICnQr_1024.jpg");
		list.add("http://img1.imgtn.bdimg.com/it/u=2978902406,2619472948&fm=214&gp=0.jpg");
		list.add("http://img.fuwo.com/attachment/1609/08/53e63eb6758c11e689ce00163e00254c.jpg");
		list.add("http://pic.58pic.com/58pic/17/09/89/26C58PICcty_1024.jpg");
		return list;
	}

	public static List<TestDto> getRooms(int size)
	{
		Random random = new Random(System.currentTimeMillis());
		List<String> images = getRoomImages();
		List<TestDto> list = new ArrayList<>();
		for(int i = 0; i < size; i++)
		{
			TestDto testVo = new TestDto();
			testVo.setTitle("测试房间信息 - "+i);
			testVo.setContent(
				i+"测试房间内容 -  测试房间内容 -  测试房间内容 -  测试房间内容 -  测试房间内容 -  测试房间内容 -  测试房间内容 -  测试房间内容 -  测试房间内容 -  ");
			testVo.setDate("2018-11-"+(i+1));
			testVo.setImage(images.get(i%images.size()));
			testVo.setCount(i+"");
			testVo.setPrice(random.nextInt()+"."+i);
			testVo.setInfo("具体房间内容信息 -  具体房间内容信息 -  具体房间内容信息 -  具体房间内容信息 -  具体房间内容信息 -  具体房间内容信息 -  具体房间内容信息 -  ");
			testVo.setBrower("http://www.ipc.me/");
			testVo.setPhone("13588273922");
			testVo.setState("未支付");
			testVo.setType(i);
			testVo.setPos(i);
			testVo.setList(listOf("String1", "String2", "String3", "String4", "String5", "String6"));
			if(i == 0)
			{
				testVo.setSelect(true);
			}
			list.add(testVo);
		}
		return list;
	}

	public static List<String> getGoodsImages()
	{
		List<String> list = new ArrayList<>();
		list.add("http://img1.imgtn.bdimg.com/it/u=2056790735,174347072&fm=27&gp=0.jpg");
		list.add("http://img1.imgtn.bdimg.com/it/u=3212438162,1933004013&fm=200&gp=0.jpg");
		list.add("http://img0.imgtn.bdimg.com/it/u=60788782,3187164422&fm=27&gp=0.jpg");
		list.add("http://img3.imgtn.bdimg.com/it/u=1483269277,2044126382&fm=27&gp=0.jpg");
		list.add("http://img0.imgtn.bdimg.com/it/u=3645570818,3958956506&fm=27&gp=0.jpg");
		list.add("http://img1.imgtn.bdimg.com/it/u=3974856663,3390286268&fm=27&gp=0.jpg");
		list.add("http://img2.imgtn.bdimg.com/it/u=1572625617,2624636735&fm=200&gp=0.jpg");
		list.add("http://img0.imgtn.bdimg.com/it/u=14743456,2599581014&fm=27&gp=0.jpg");
		list.add("http://img0.imgtn.bdimg.com/it/u=619783351,837496103&fm=27&gp=0.jpg");
		list.add("http://img1.imgtn.bdimg.com/it/u=757461383,3477003302&fm=27&gp=0.jpg");
		return list;
	}

	public static List<TestDto> getGoods(int size)
	{
		Random random = new Random(System.currentTimeMillis());
		List<String> images = getGoodsImages();
		List<TestDto> list = new ArrayList<>();
		for(int i = 0; i < size; i++)
		{
			TestDto testVo = new TestDto();
			testVo.setTitle("测试商品标题 - "+i);
			testVo.setContent(i+"测试商品内容 -  测试商品内容 -  测试商品内容 -  测试商品内容 -  测试商品内容 -  测试商品内容 -  测试商品内容 -  测试商品内容 -  ");
			testVo.setDate("2018-11-"+(i+1));
			testVo.setImage(images.get(i%images.size()));
			testVo.setCount(i+"");
			testVo.setPrice(random.nextInt()+"."+i);
			testVo.setInfo(i+"具体商品内容信息 -  具体商品内容信息 -  具体商品内容信息 -  具体商品内容信息 -  具体商品内容信息 -  具体商品内容信息 -  具体商品内容信息 -  ");
			testVo.setBrower("http://www.ipc.me/");
			testVo.setPhone("13588273922");
			testVo.setState("未支付");
			testVo.setType(i);
			testVo.setPos(i);
			testVo.setList(listOf("String1", "String2", "String3", "String4", "String5", "String6"));
			if(i == 0)
			{
				testVo.setSelect(true);
			}
			list.add(testVo);
		}
		return list;
	}

	public static List<String> getScenicImages()
	{
		List<String> list = new ArrayList<>();
		list.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1170915064,355425524&fm=27&gp=0.jpg");
		list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2366150405,2276269158&fm=200&gp=0.jpg");
		list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512641830296&di=b9615503c5f4df7ebce2567ecc33ad6d&imgtype=0&src=http%3A%2F%2Fwww.anys.cc%2Fuploads%2F141009%2F1_201716_1.jpg");
		list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512641843520&di=30998b58f78183580825e1e09a44c764&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F140701%2F240400-140F10S64630.jpg");
		list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512641844118&di=09010b9d31e0a474d70b133f0c501fd8&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fe4dde71190ef76c6adf2b8119616fdfaaf5167e3.jpg");
		list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512641845828&di=1ae5e4467cb708844c233bc9ac935dcc&imgtype=0&src=http%3A%2F%2Fyouimg1.c-ctrip.com%2Ftarget%2Ffd%2Ftg%2Fg2%2FM00%2F4C%2FAE%2FCghzgFWuGAKANAp-AB-6BzbDHew623.jpg");
		list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512641889748&di=948f0b9d41356411c4530b7ab00fc219&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F0824ab18972bd407dfce0bf471899e510fb309aa.jpg");
		list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512641891389&di=54226895239e7db495c87c37e83ca524&imgtype=0&src=http%3A%2F%2Fyouimg1.c-ctrip.com%2Ftarget%2Ftg%2F091%2F509%2F069%2F43534ce5d50b46a9973c95f848c80c81.jpg");
		list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512641893057&di=4a02c7c925a2a908b6ea5952d0897f84&imgtype=0&src=http%3A%2F%2Fdimg02.c-ctrip.com%2Fimages%2Ffd%2Ftg%2Fg2%2FM0A%2FDD%2FF8%2FCghzgVTHSImAe3o8AAe1chlU5WM737.jpg");
		list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512641897007&di=7ca968bd15888b2ac1db73f3dcc46234&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D69d740986f380cd7f213aaaec92dc741%2F902397dda144ad34802b38afdaa20cf431ad8506.jpg");
		return list;
	}


	public static List<TestDto> getScenics(int size)
	{
		Random random = new Random(System.currentTimeMillis());
		List<String> images = getScenicImages();
		List<TestDto> list = new ArrayList<>();
		for(int i = 0; i < size; i++)
		{
			TestDto testVo = new TestDto();
			testVo.setTitle("测试景区标题 - "+i);
			testVo.setContent(i+"测试景区内容 -  测试景区内容 -  测试景区内容 -  测试景区内容 -  测试景区内容 -  测试景区内容 -  测试景区内容 -  测试景区内容 -  ");
			testVo.setDate("2018-11-"+(i+1));
			testVo.setImage(images.get(i%images.size()));
			testVo.setCount(i+"");
			testVo.setPrice(random.nextInt()+"."+i);
			testVo.setInfo(i+"具体景区内容信息 -  具体景区内容信息 -  具体景区内容信息 -  具体景区内容信息 -  具体景区内容信息 -  具体景区内容信息 -  具体景区内容信息 -  ");
			testVo.setBrower("http://www.ipc.me/");
			testVo.setPhone("13588273922");
			testVo.setState("未支付");
			testVo.setType(i);
			testVo.setPos(i);
			testVo.setList(listOf("String1", "String2", "String3", "String4", "String5", "String6"));
			if(i == 0)
			{
				testVo.setSelect(true);
			}
			list.add(testVo);
		}
		return list;
	}

	public static List<String> getBanner()
	{
		List<String> list = new ArrayList<>();
		list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512642203946&di=e5e5e49e242eff062a3d564603504fdf&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01336657bd05390000012e7ebb7df5.jpg");
		list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512642023072&di=c769891ddaac15e0ec4a457d8916ca8d&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01f85a574f83a36ac72525ae18700e.jpg");
		list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512642194644&di=a6fe94d74af1a6dd80f1c655ddbd33a0&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01511c57f9fc0ea84a0d304f5dc0c0.jpg");
		list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512642214877&di=7cc6686e19a7b5933fd175650247043e&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0153b557f9f9a0a84a0d304fb91a49.jpg%40900w_1l_2o_100sh.jpg");
		list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512642268389&di=27f50ca3cb0368c418d58345ba8994bf&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fback_pic%2Fqk%2Fback_origin_pic%2F00%2F01%2F40%2F2a8f5f1657f5f93d3f6fa2d57594ce51.jpg");
		return list;
	}


	public static String getHttpString()
	{
		return "<div id=\"footer\"><div id=\"footer-body\">\n"+
			"<a id=\"footer-logo\" href=\"http://www.ipc.me\" rel=\"nofollow\"></a><div id=\"footer-content\">\n"+
			"<a href=\"http://www.iplaysoft.com\" target=\"_blank\" rel=\"nofollow\">异次元软件世界</a> 旗下网站  |  <a href=\"http://www.iplaysoft.com/go/store\" target=\"_blank\" rel=\"nofollow\">正版数字商城</a>  |  基于 <a href=\"http://cn.wordpress.org/\" target=\"_blank\" rel=\"nofollow\">WordPress</a> 技术构建  |  <a title=\"站点地图\" href=\"http://www.ipc.me/sitemap.html\">站点地图</a>  |  <a href=\"http://www.miibeian.gov.cn/\" target=\"_blank\" rel=\"nofollow\">粤ICP备06080643号</a>  |  本站使用 <a href=\"http://www.iplaysoft.com/go/aliyun\" target=\"_blank\" rel=\"nofollow\">阿里云主机</a>+<a href=\"http://www.iplaysoft.com/go/linode\" target=\"_blank\" rel=\"nofollow\">Linode</a></div></div><style type=\"text/css\">#goToTop{display:block;position:fixed;bottom:25px;right:25px;z-index:9999;_bottom:none;_right:10px;_position:absolute;_top:expression(eval(document.documentElement.scrollTop+document.documentElement.clientHeight-this.offsetHeight-(parseInt(this.currentStyle.marginTop, 10)||0)-(parseInt(this.currentStyle.marginBottom, 10)||0))-10);}#footer #goToTop a,#goToTop a{display:block;text-indent:-99999px;width:56px;height:56px;background:url(\"http://cdn.iplaysoft.com/common/gototop/gototop.png\") no-repeat;_background:#eee;_text-indent:0;_border:1px solid #ccc;_width:0;_height:0;_padding:5px 10px;}</style><div id=\"goToTop\" style=\"left: 1465px; display: none;\"><a href=\"#\">回到顶部</a></div><div class=\"entry-content\" itemprop=\"description\"><p class=\"entry-image\"><a href=\"http://www.ipc.me/weight-of-galaxy.html\" title=\"科学家计算出银河系最精确“体重”，相当于2100亿个太阳质量！\" itemprop=\"url\"><img border=\"0\" width=\"450\" height=\"250\" itemprop=\"image\" src=\"http://ipc.chotee.com/uploads/post/15386/557e4ba673a96.jpg\"></a></p><p>据国外媒体报道，<a href=\"http://www.ipc.me/weight-of-galaxy.html\">银河系最精确的“体重”</a>出炉——相当于2100亿个太阳的质量。这一结果可帮助科学确认银河有究竟有多大……</p></div></div>";
	}
}
