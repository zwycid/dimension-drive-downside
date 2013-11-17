package mju.t3rd.sailingtext.lk.option.tutorial;

import mju.t3rd.sailingtext.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ListAdapter extends BaseAdapter {

//	private Context Context;

	private ImageView imageView;

	public ListAdapter(Context context) {
//		Context = context;
		imageView	= new ImageView(context);
		imageView.setImageResource(R.drawable.help_contents);
	}

	public void setListItems(ImageView lit) {
		imageView = lit;
	}

	public int getCount() {
		return 1;
	}

	public Object getItem(int position) {
		return imageView;
	}

	public boolean areAllItemsSelectable() {
		return false;
	}

	public boolean isSelectable(int position) {
			return false;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView() {
		return imageView;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		return imageView;
	}

}
