package com.rnr.paperfootball.ui;

import java.util.Locale;
import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.rnr.paperfootball.base.BaseMap;
import com.rnr.paperfootball.base.BaseMapController;
import com.rnr.paperfootball.base.BasePlayer;
import com.rnr.paperfootball.core.Game;
import com.rnr.paperfootball.core.GameCallback;
import com.rnr.paperfootball.core.TouchHandler;
import com.rnr.paperfootball.core.WrongPathException;

public class GameFieldView extends SurfaceView implements SurfaceHolder.Callback, GameCallback,
		OnTouchListener {
	private BaseMapController mPainter;
	private boolean mSurfaceCreated;
	private Vector<TouchHandler> mTouchHandler;
	private Game mGame;
	private Rect mField;
	private int mWidth;
	private int mHeight;

	public void copyFrom(GameFieldView fv) {
        this.setGame(fv.mGame);
        this.setMapPainter(fv.mPainter);
    	this.mTouchHandler = new Vector<TouchHandler>(fv.mTouchHandler);
	}
	
	public void addHandler(TouchHandler handler) {
		this.mTouchHandler.add(handler);
	}

	public void removeHandler(TouchHandler handler) {
		this.mTouchHandler.remove(handler);
	}

	private int setTextSize(Paint paint, String str, float maxWidth)
	{
	    int size = 0;       

	    do {
	        paint.setTextSize(++size);
	    } while(paint.measureText(str) < maxWidth);

	    return size;
	}
	
	private void paintField(SurfaceHolder surfaceHolder) {
		if (!this.mSurfaceCreated) {
			return;
		}
        Canvas C = surfaceHolder.lockCanvas();

        try {
    		int width = this.mWidth - 6;
    		int height = this.mHeight - 6;
    		
    		Picture picture = new Picture();

    		Rect rect = this.mPainter.getRect(width, height);

    		
        	this.mField = new Rect((width - rect.width()) / 2 + 3, 
        			3, (width + rect.width()) / 2,  
        			rect.height());

        	Canvas canvas = picture.beginRecording(this.mField.width(), this.mField.height());
        	this.mPainter.draw(canvas);
    		C.drawPicture(picture, this.mField);

    		if (width >= height) {
    			BasePlayer player = this.mGame.getPlayers().get(0);  
				Paint pnt = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
				pnt.setColor(player.getColor());
				pnt.setTextAlign(Paint.Align.RIGHT);
				String str = String.format(Locale.getDefault(), "%d", player.getWins());
				pnt.setTextSize(this.mField.height() / 8);
				C.drawText(str, this.mField.left - 5, 
						this.mField.centerY() + pnt.getTextSize() / 2, pnt);

				player = this.mGame.getPlayers().get(1);  
				pnt = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
				pnt.setColor(player.getColor());
				pnt.setTextAlign(Paint.Align.LEFT);
				str = String.format(Locale.getDefault(), "%d", player.getWins());
				pnt.setTextSize(this.mField.height() / 8);
				C.drawText(str, this.mField.right + 5, 
						this.mField.centerY() + pnt.getTextSize() / 2, pnt);
    		} else {
    			BasePlayer player = this.mGame.getPlayers().get(0);  
				Paint pnt = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
				pnt.setColor(player.getColor());
				pnt.setTextAlign(Paint.Align.LEFT);
				String str = String.format(Locale.getDefault(), "%s", 
						player.getName());
				pnt.setTextSize(this.mField.height() / 12);
				C.drawText(str, this.mField.left, 
						this.mField.bottom + this.mField.height() / 16, pnt);

				pnt.setColor(Color.WHITE);
				pnt.setTextAlign(Paint.Align.CENTER);
				int leftWins = player.getWins();
				
				player = this.mGame.getPlayers().get(1);
				int rightWins = player.getWins();
				str = String.format(Locale.getDefault(), "%d - %d", 
						leftWins, rightWins);
				C.drawText(str, this.mField.centerX() , 
						(float) (this.mField.bottom + this.mField.height() / 16 + 1.2 * pnt.getTextSize()), pnt);

				pnt.setColor(player.getColor());
				pnt.setTextAlign(Paint.Align.RIGHT);
				str = String.format(Locale.getDefault(), "%s", 
						player.getName());
				C.drawText(str, this.mField.right , 
						(float) (this.mField.bottom + this.mField.height() / 16 + 2.4 * pnt.getTextSize()), pnt);
    		}
        } finally{
        	surfaceHolder.unlockCanvasAndPost(C);
        }
	}

	protected void initView() {
		this.setOnTouchListener(this);
		this.mSurfaceCreated = false;
		this.mTouchHandler = new Vector<TouchHandler>();
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

	public void setMapPainter(BaseMapController mapPainter) {
		this.mPainter = mapPainter;
	}
	public void setGame(Game game) {
		this.mGame = game;
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int arg1, int arg2, int arg3) {
		this.paintField(surfaceHolder);
	}

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		this.mSurfaceCreated = true;
		this.paintField(surfaceHolder);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		this.mSurfaceCreated = false;
	}

	@Override
	public void repaint(BaseMap map) {
		this.paintField(this.getHolder());
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		for (TouchHandler handler : this.mTouchHandler) {
			try {
				if (handler.setPoint(this.mPainter, event.getX() - this.mField.left, 
						event.getY() - this.mField.top)) {
					this.paintField(this.getHolder());
				}
			} catch (WrongPathException e) {
				Toast.makeText(getContext(), e.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
		}
		return false;
	}

	@Override
	public void endOfGame(int indexPlayer) {
		final int closureIndexPlayer = indexPlayer;
		mGame.getPlayers().get(closureIndexPlayer).incWins();
		this.mPainter.setCurrentPath(null);
		this.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getContext(),
						String.format("Победил игрок: %s",
								mGame.getPlayers().get(closureIndexPlayer).getName()),
						Toast.LENGTH_LONG).show();
			}
		});
		mGame.sendRepaint();
	}

	 @Override
	 protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec)
	 {
	     super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	     this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
	     this.mHeight = MeasureSpec.getSize(heightMeasureSpec);
	 }
}
