package com.seaSaltedToaster.simpleEngine.uis.constraints.position;

import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.ConstraintType;
import com.seaSaltedToaster.simpleEngine.uis.constraints.HorizontalAlignment;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraint;

public class AlignX extends UiConstraint {
	
	private HorizontalAlignment alignment;
	private float gap;
	
	public AlignX(HorizontalAlignment alignment) {
		this.alignment = alignment;
		this.gap = 0;
	}
	
	public AlignX(HorizontalAlignment alignment, float gap) {
		this.alignment = alignment;
		this.gap = gap;
	}
	
	@Override
	protected
	void update(UiComponent component, ConstraintType type) {
		if(type != ConstraintType.X) return;
		UiComponent componentParent = component.getParentComponent();
		
		
		float gapDist = gap * componentParent.getScale().x;
		
		switch (alignment) {
		case CENTER:
			component.getPosition().setX(componentParent.getPosition().x + gap);
			break;
		case LEFT:
			float distLeft = componentParent.getPosition().x - (componentParent.getScale().x - component.getScale().x);
			component.getPosition().setX(distLeft + gapDist);
			break;
		case RIGHT:
			float distRight = componentParent.getPosition().x + (componentParent.getScale().x - component.getScale().x);
			component.getPosition().setX(distRight - gapDist);
			break;
		default:
			break;
		}
	}

	public HorizontalAlignment getAlignment() {
		return alignment;
	}

	public void setAlignment(HorizontalAlignment alignment) {
		this.alignment = alignment;
	}

	public float getGap() {
		return gap;
	}

	public void setGap(float gap) {
		this.gap = gap;
	}

}
