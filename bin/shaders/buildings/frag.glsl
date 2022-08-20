#version 400

const vec4 nightColor = vec4(0.0f);
const vec3 primaryInd = vec3(0.1f, 0.15f, 0.2f);

in flat vec3 pass_color;

uniform bool isPreview;

out vec4 out_Color;

uniform int currentId;
uniform int selectedId;
uniform float dayValue;

void main(void)	{

	out_Color = vec4(pass_color, 1.0f);
	
	if(out_Color.xyz == primaryInd) {
		out_Color = vec4(1,0,0,0);
	}
		
	out_Color = mix(out_Color, nightColor, dayValue);
	
	if(currentId == selectedId) {
		out_Color *= 2;
	}
	
	//preview
	if(currentId == -1) {
		out_Color *= 2;
	}

}
