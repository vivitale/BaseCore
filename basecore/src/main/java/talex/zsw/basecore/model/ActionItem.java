package talex.zsw.basecore.model;

/**
 * 功能描述：弹窗内部子类项（绘制标题和图标）
 */
public class ActionItem
{
	/**
	 * 定义文本对象
	 */
	public CharSequence mTitle;

	public int mResourcesId;

	public ActionItem(CharSequence title, int mResourcesId)
	{
		this.mResourcesId = mResourcesId;
		this.mTitle = title;
	}

	public ActionItem(CharSequence title)
	{
		this.mTitle = title;
	}

	public CharSequence getmTitle()
	{
		return mTitle;
	}

	public void setmTitle(CharSequence mTitle)
	{
		this.mTitle = mTitle;
	}

	public int getmResourcesId()
	{
		return mResourcesId;
	}

	public void setmResourcesId(int mResourcesId)
	{
		this.mResourcesId = mResourcesId;
	}

	@Override public String toString()
	{
		return "ActionItem{"+"mTitle="+mTitle+", mResourcesId="+mResourcesId+'}';
	}
}