package de.bockstallmann.interaktive.vorlesung.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class BarView extends View {

	private int w;
	private Paint paint = new Paint();

	public BarView(Context context, int width) {
		super(context);
		this.w = width;
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	}
	@Override
	protected void onDraw(Canvas canvas) {
		 paint .setColor(Color.BLACK);
		 Log.d("BarView", "width: "+w);
		canvas.drawRect(0, 0, w, 20, paint);
	}
	

}
