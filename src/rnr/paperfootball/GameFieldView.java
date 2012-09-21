package rnr.paperfootball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameFieldView extends SurfaceView implements SurfaceHolder.Callback, GameCallback {
	private BaseMapPainter mPainter;

	private void paintField(SurfaceHolder surfaceHolder) {
        Canvas C = surfaceHolder.lockCanvas();

        try {
    		Picture picture = new Picture();

    		Canvas canvas = picture.beginRecording(500, 500);
        	this.mPainter.draw(canvas);
    		picture.draw(C);
        } finally{
        	surfaceHolder.unlockCanvasAndPost(C);
        }
	}

	protected void initView() {
		this.getHolder().addCallback(this);
	}

	public GameFieldView(Context context) {
		super(context);
		this.initView();
	}

	public GameFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initView();
	}

	public GameFieldView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.initView();
	}

	public void setMapPainter(BaseMapPainter mapPainter) {
		this.mPainter = mapPainter;
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int arg1, int arg2, int arg3) {
		this.paintField(surfaceHolder);
	}
	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		this.paintField(surfaceHolder);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
	}

	@Override
	public void repaint(BaseGameMap map) {
		this.paintField(this.getHolder());
	}

}
