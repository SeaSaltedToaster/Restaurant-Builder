package com.seaSaltedToaster.simpleEngine.uis.constraints.position;

import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.ConstraintType;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraint;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;

public class AlignY extends UiConstraint {

	private YAlign alignment;
	private float gap;
	
	public AlignY(YAlign alignment) {
		this.alignment = alignment;
		this.gap = 0;
	}
	
	public AlignY(YAlign alignment, float gap) {
		this.alignment = alignment;
		this.gap = gap;
	}
	
	@Override
	protected
	void update(UiComponent component, ConstraintType type) {
		if(type != ConstraintType.Y) return;
		UiComponent componentParent = component.getParentComponent();
		
		float gapDist = gap * componentParent.getScale().y;
		
		switch (alignment) {
		case MIDDLE:
			component.getPosition().setY(componentParent.getPosition().y);
			break;
		case BOTTOM:
			float distBottom = componentParent.getPosition().y - (componentParent.getScale().y - component.getScale().y);
			component.getPosition().setY(distBottom + gapDist);
			break;
		case TOP:
			float distTop = componentParent.getPosition().y + (componentParent.getScale().y - component.getScale().y);
			component.getPosition().setY(distTop - gapDist);
			break;
		default:
			break;
		}
	}

	public YAlign getAlignment() {
		return alignment;
	}

	public void setAlignment(YAlign alignment) {
		this.alignment = alignment;
	}

	public float getGap() {
		return gap;
	}

	public void setGap(float gap) {
		this.gap = gap;
	}

}
