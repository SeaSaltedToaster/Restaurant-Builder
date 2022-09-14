package com.seaSaltedToaster.simpleEngine.uis.constraints;

import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.uis.layouts.UiLayout;

public class UiConstraints {

	private UiConstraint xConstraint, yConstraint;
	private UiConstraint xScaleConstraint, yScaleConstraint;
	
	private UiLayout layout;
	
	public UiConstraints(UiConstraint xConstraint, UiConstraint yConstraint, UiConstraint xScaleConstraint,
			UiConstraint yScaleConstraint, UiLayout layout) {
		this.xConstraint = xConstraint;
		this.yConstraint = yConstraint;
		this.xScaleConstraint = xScaleConstraint;
		this.yScaleConstraint = yScaleConstraint;
		this.layout = layout;
	}
	
	public UiConstraints() {
		this.xConstraint = null;
		this.yConstraint = null;
		this.xScaleConstraint = null;
		this.yScaleConstraint = null;
	}
	
	public void updateConstraints(UiComponent component) {
		if(component.getParentComponent() != null && component.getParentComponent().getConstraints().getLayout() != null)
			component.getParentComponent().getConstraints().getLayout().update(component.getParentComponent(), component.getParentComponent().getChildren());
		if(xConstraint != null)
			xConstraint.update(component, ConstraintType.X);
		if(yConstraint != null)
			yConstraint.update(component, ConstraintType.Y);
		if(xScaleConstraint != null)
			xScaleConstraint.update(component, ConstraintType.WIDTH);
		if(yScaleConstraint != null)
			yScaleConstraint.update(component, ConstraintType.HEIGHT);
	}
	
	public static UiConstraints getCentered() {
		return new UiConstraints().setX(new AlignX(XAlign.CENTER)).setY(new AlignY(YAlign.MIDDLE));
	}
	
	public static UiConstraints getFill() {
		return new UiConstraints().setWidth(new RelativeScale(1.0f)).setHeight(new RelativeScale(1.0f));
	}
	
	public static UiConstraints getFillCenter() {
		return new UiConstraints().setWidth(new RelativeScale(1.0f)).setHeight(new RelativeScale(1.0f)).setX(new AlignX(XAlign.CENTER)).setY(new AlignY(YAlign.MIDDLE));
	}

	public UiConstraint getXConstraint() {
		return xConstraint;
	}

	public UiConstraint getYConstraint() {
		return yConstraint;
	}

	public UiConstraint getXScaleConstraint() {
		return xScaleConstraint;
	}

	public UiConstraint getYScaleConstraint() {
		return yScaleConstraint;
	}

	public UiConstraints setX(UiConstraint xConstraint) {
		this.xConstraint = xConstraint;
		return this;
	}

	public UiConstraints setY(UiConstraint yConstraint) {
		this.yConstraint = yConstraint;
		return this;
	}

	public UiConstraints setWidth(UiConstraint xScaleConstraint) {
		this.xScaleConstraint = xScaleConstraint;
		return this;
	}

	public UiConstraints setHeight(UiConstraint yScaleConstraint) {
		this.yScaleConstraint = yScaleConstraint;
		return this;
	}

	public UiLayout getLayout() {
		return layout;
	}

	public UiConstraints setLayout(UiLayout layout) {
		this.layout = layout;
		return this;
	}
	
}
