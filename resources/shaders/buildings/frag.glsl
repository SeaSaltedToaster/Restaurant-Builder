#version 400

const vec4 nightColor = vec4(0.0f);

const vec3 primaryInd = vec3(0.1f, 0.15f, 0.2f);
const vec3 secondaryInd = vec3(0.2f, 0.15f, 0.1f);

in flat vec3 pass_color;

uniform bool isPreview;

out vec4 out_Color;

uniform int currentId;
uniform int selectedId;
uniform float dayValue;

uniform vec3 primaryColor;
uniform vec3 secondaryColor;

void main(void)	{

	out_Color = vec4(pass_color, 1.0f);
	
	if(out_Color.x == 2) {
		float offset = out_Color.z;
		out_Color = vec4(primaryColor + vec3(offset), 0.0f);
	}
	
	if(out_Color.x == 3) {
		float offset = out_Color.z;
		out_Color = vec4(secondaryColor + vec3(offset), 0.0f);
	}
		
	out_Color = mix(out_Color, nightColor, dayValue);
	
	if(currentId == selectedId || currentId == -1) {
		out_Color = vec4(1.0f);
	}

}
