package talex.zsw.sample.test;

import java.io.Serializable;
import java.util.List;

/**
 * 作用：测试类
 * 作者：赵小白 email:vvtale@gmail.com  
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TestDto implements Serializable
{
	private String image;
	private String title;
	private String content;
	private String count;
	private String info;
	private String date;
	private String price;
	private String brower;
	private String repost;
	private String phone;
	private List<String> list;
	private String state;
	private int type;
	private int pos;
	private boolean isFlag;
	private boolean isSelect;

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getCount()
	{
		return count;
	}

	public void setCount(String count)
	{
		this.count = count;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getPrice()
	{
		return price;
	}

	public void setPrice(String price)
	{
		this.price = price;
	}

	public String getBrower()
	{
		return brower;
	}

	public void setBrower(String brower)
	{
		this.brower = brower;
	}

	public String getRepost()
	{
		return repost;
	}

	public void setRepost(String repost)
	{
		this.repost = repost;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public List<String> getList()
	{
		return list;
	}

	public void setList(List<String> list)
	{
		this.list = list;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public boolean isFlag()
	{
		return isFlag;
	}

	public void setFlag(boolean flag)
	{
		isFlag = flag;
	}

	public boolean isSelect()
	{
		return isSelect;
	}

	public void setSelect(boolean select)
	{
		isSelect = select;
	}

	public int getPos()
	{
		return pos;
	}

	public void setPos(int pos)
	{
		this.pos = pos;
	}
}
