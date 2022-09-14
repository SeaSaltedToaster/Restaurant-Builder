#version 400

const vec4 nightColor = vec4(0.0f);
const vec3 primaryInd = vec3(0.1f, 0.15f, 0.2f);

in flat vec3 pass_color;

uniform bool isPreview;

out vec4 out_Color;

uniform int currentId;
uniform int selectedId;
uniform float dayValue;

uniform vec3 customColor;

void main(void)	{

	out_Color = vec4(pass_color, 1.0f);
	
	if(out_Color.xyz == primaryInd) {
		out_Color = vec4(customColor, 0);
	}
		
	out_Color = mix(out_Color, nightColor, dayValue);
	
	if(currentId == selectedId) {
		vec4 tint = vec4(0.75, 0.75, 1.0, 1.0);
		out_Color *= tint;
	}
	
	//preview
	if(currentId == -1) {
		out_Color *= 2;
	}

}
