package yin.style.recyclerlib.flowlayoutmanager;

/**
 * Created by 陈银 on 2016/4/11 12:25
 *
 * 描述：
 */
public class LayoutContext {
	public FlowLayoutOptions layoutOptions;
	public int currentLineItemCount;

	public static LayoutContext clone(LayoutContext layoutContext) {
		LayoutContext resultContext = new LayoutContext();
		resultContext.currentLineItemCount = layoutContext.currentLineItemCount;
		resultContext.layoutOptions = FlowLayoutOptions.clone(layoutContext.layoutOptions);
		return resultContext;
	}

	public static LayoutContext fromLayoutOptions(FlowLayoutOptions layoutOptions) {
		LayoutContext layoutContext = new LayoutContext();
		layoutContext.layoutOptions = layoutOptions;
		return layoutContext;
	}
}
