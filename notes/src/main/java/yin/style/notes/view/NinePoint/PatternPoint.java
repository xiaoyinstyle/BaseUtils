package yin.style.notes.view.NinePoint;

/**
 * Created by Chne on 2017/8/12.
 */
public class PatternPoint extends PatternPointBase {
    protected static final int MIN_SIDE = 20;        //最小边长
    protected static final int MIN_PADDING = 4;        //最小间隔
    protected static final int MIN_RADIUS = 6;        //最小半径

    protected int left, top, side, padding;     //side:边长

    public PatternPoint(int left, int top, int side, int padding, String tag) {
        this.left = left;
        this.top = top;
        this.tag = tag;

        if (side < MIN_SIDE) {
            side = MIN_SIDE;
        }
        this.side = side;

        if (padding < MIN_PADDING) {
            padding = MIN_PADDING;
        }

        radius = side / 2 - padding;
        if (radius < MIN_RADIUS) {
            radius = MIN_RADIUS;
            padding = side / 2 - radius;
        }
        this.padding = padding;
        centerX = left + side / 2;
        centerY = top + side / 2;
        status = STATE_NORMAL;
    }
}
