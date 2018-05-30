package talex.zsw.basecore.view.other.dropdownmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import talex.zsw.basecore.R;

public class ConstellationAdapter extends BaseAdapter
{

	private Context context;
	private List<String> list;
	private int checkItemPosition = 0;

	private int checkedTC = R.color.tomato, uncheckTC = R.color.black2;
	private int checkedRs = R.drawable.check_bg;
	private int uncheckedRs = R.drawable.uncheck_bg;

	public void setCheckItem(int position)
	{
		checkItemPosition = position;
		notifyDataSetChanged();
	}

	public ConstellationAdapter(Context context, List<String> list)
	{
		this.context = context;
		this.list = list;
	}

	public ConstellationAdapter(Context context, List<String> list, int checkedTC, int uncheckTC,int checkedRs,int uncheckedRs)
	{
		this.context = context;
		this.list = list;
		this.checkedTC = checkedTC;
		this.uncheckTC = uncheckTC;
		this.checkedRs = checkedRs;
		this.uncheckedRs = uncheckedRs;
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
				LayoutInflater.from( context ).inflate( R.layout.item_constellation_layout, null );
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
				viewHolder.mText.setBackgroundResource( R.drawable.check_bg );
			}
			else
			{
				viewHolder.mText.setTextColor( context.getResources().getColor( uncheckTC ) );
				viewHolder.mText.setBackgroundResource( R.drawable.uncheck_bg );
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

	public int getCheckedTC()
	{
		return checkedTC;
	}

	public void setCheckedTC(int checkedTC)
	{
		this.checkedTC = checkedTC;
	}

	public int getUncheckTC()
	{
		return uncheckTC;
	}

	public void setUncheckTC(int uncheckTC)
	{
		this.uncheckTC = uncheckTC;
	}

	public int getCheckedRs()
	{
		return checkedRs;
	}

	public void setCheckedRs(int checkedRs)
	{
		this.checkedRs = checkedRs;
	}

	public int getUncheckedRs()
	{
		return uncheckedRs;
	}

	public void setUncheckedRs(int uncheckedRs)
	{
		this.uncheckedRs = uncheckedRs;
	}
}
