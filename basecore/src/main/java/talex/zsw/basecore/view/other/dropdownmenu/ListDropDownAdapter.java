package talex.zsw.basecore.view.other.dropdownmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import talex.zsw.basecore.R;

public class ListDropDownAdapter extends BaseAdapter
{

	private Context context;
	private List<String> list;
	private int checkItemPosition = 0;

	private int checkedBG = R.color.ghostwhite, checkedTC = R.color.tomato, uncheckBG =
		R.color.white, uncheckTC = R.color.black2;

	public void setCheckItem(int position)
	{
		checkItemPosition = position;
		notifyDataSetChanged();
	}

	public ListDropDownAdapter(Context context, List<String> list)
	{
		this.context = context;
		this.list = list;
	}

	public ListDropDownAdapter(Context context, List<String> list, int checkedBG, int checkedTC,
		int uncheckBG, int uncheckTC)
	{
		this.context = context;
		this.list = list;
		this.checkedBG = checkedBG;
		this.checkedTC = checkedTC;
		this.uncheckBG = uncheckBG;
		this.uncheckTC = uncheckTC;
	}

	@Override public int getCount()
	{
		return list.size();
	}

	@Override public Object getItem(int position)
	{
		return null;
	}

	@Override public long getItemId(int position)
	{
		return position;
	}

	@Override public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder;
		if(convertView != null)
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		else
		{
			convertView =
				LayoutInflater.from( context ).inflate( R.layout.item_default_drop_down, null );
			viewHolder = new ViewHolder( convertView );
			convertView.setTag( viewHolder );
		}
		fillValue( position, viewHolder );
		return convertView;
	}

	private void fillValue(int position, ViewHolder viewHolder)
	{
		viewHolder.mText.setText( list.get( position ) );
		if(checkItemPosition != -1)
		{
			if(checkItemPosition == position)
			{
				viewHolder.mText.setTextColor( context.getResources().getColor( checkedTC ) );
				viewHolder.mText.setBackgroundResource( checkedBG );
			}
			else
			{
				viewHolder.mText.setTextColor( context.getResources().getColor( uncheckTC ) );
				viewHolder.mText.setBackgroundResource( uncheckBG );
			}
		}
	}

	static class ViewHolder
	{
		private TextView mText;

		ViewHolder(View view)
		{
			mText = (TextView) view.findViewById( R.id.text );
		}
	}

	public int getCheckedBG()
	{
		return checkedBG;
	}

	public void setCheckedBG(int checkedBG)
	{
		this.checkedBG = checkedBG;
	}

	public int getCheckedTC()
	{
		return checkedTC;
	}

	public void setCheckedTC(int checkedTC)
	{
		this.checkedTC = checkedTC;
	}

	public int getUncheckBG()
	{
		return uncheckBG;
	}

	public void setUncheckBG(int uncheckBG)
	{
		this.uncheckBG = uncheckBG;
	}

	public int getUncheckTC()
	{
		return uncheckTC;
	}

	public void setUncheckTC(int uncheckTC)
	{
		this.uncheckTC = uncheckTC;
	}
}
