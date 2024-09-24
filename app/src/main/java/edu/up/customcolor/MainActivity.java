package edu.up.customcolor;
/*
Ryan Murray
9/21/2024
 */
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity implements SurfaceHolder.Callback, SeekBar.OnSeekBarChangeListener {
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private int red, green, blue, shape;
    private TextView textView;
    private SeekBar shapeSeekBar, redSeekBar, greenSeekBar, blueSeekBar;
    private int[] shapeColors = new int[6]; // array to store colors for each shape

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView = findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        paint = new Paint();

        textView = findViewById(R.id.textView);
        shapeSeekBar = findViewById(R.id.shapeSeekBar);
        redSeekBar = findViewById(R.id.redSeekBar);
        greenSeekBar = findViewById(R.id.greenSeekBar);
        blueSeekBar = findViewById(R.id.blueSeekBar);

        shapeSeekBar.setOnSeekBarChangeListener(this);
        redSeekBar.setOnSeekBarChangeListener(this);
        greenSeekBar.setOnSeekBarChangeListener(this);
        blueSeekBar.setOnSeekBarChangeListener(this);

        // initialize shape colors with default values
        for (int i = 0; i < 6; i++) {
            shapeColors[i] = Color.BLACK;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawSnowman();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == shapeSeekBar) {
            shape = progress;
            updateShape();
        } else if (seekBar == redSeekBar) {
            red = progress;
            updateColor();
        } else if (seekBar == greenSeekBar) {
            green = progress;
            updateColor();
        } else if (seekBar == blueSeekBar) {
            blue = progress;
            updateColor();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private void updateColor() {
        paint.setColor(Color.rgb(red, green, blue));
        drawSnowman();
    }

    private void updateShape() {
        textView.setText("Current shape: " + getShapeName(shape));
        drawSnowman();

        // save current color to shapeColors array
        shapeColors[shape] = Color.rgb(red, green, blue);
    }

    private String getShapeName(int shape) {
        switch (shape) {
            case 0:
                return "Body";
            case 1:
                return "Hat";
            case 2:
                return "Nose";
            case 3:
                return "Background";
            case 4:
                return "Left Eye";
            case 5:
                return "Right Eye";
            default:
                return "Unknown";
        }
    }

    private void drawSnowman() {
        Canvas canvas = surfaceView.getHolder().lockCanvas();
        canvas.drawColor(Color.WHITE); // clear canvas

        // draw background
        paint.setColor(Color.rgb(red, green, blue));
        canvas.drawRect(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), paint);

        // draw body
        paint.setColor(shapeColors[0]);
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 100, paint);

        // draw hat
        paint.setColor(shapeColors[1]);
        canvas.drawRect(new RectF(canvas.getWidth() / 2 - 50, canvas.getHeight() / 2 - 150, canvas.getWidth() / 2 + 50, canvas.getHeight() / 2 - 100), paint);

        // draw nose
        paint.setColor(shapeColors[2]);
        canvas.drawRect(new RectF(canvas.getWidth() / 2 - 10, canvas.getHeight() / 2 - 50, canvas.getWidth() / 2 + 10, canvas.getHeight() / 2 - 30), paint);

        // draw left eye
        paint.setColor(shapeColors[4]);
        canvas.drawCircle(canvas.getWidth() / 2 - 30, canvas.getHeight() / 2 - 70, 10, paint);

        // draw right eye
        paint.setColor(shapeColors[5]);
        canvas.drawCircle(canvas.getWidth() / 2 + 30, canvas.getHeight() / 2 - 70, 10, paint);

        surfaceHolder.unlockCanvasAndPost(canvas);
    }
}