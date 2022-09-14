#version 400

const float shade = 2.0;
const vec4 nightColor = vec4(0.0f);

in vec3 pass_color;
in flat int pass_id;

in vec4 shadowCoords;

out vec4 out_Color;

uniform sampler2D shadowMap;

uniform int selected;
uniform float dayValue;

void main(void)	{

	float objectNearestLight = texture(shadowMap, shadowCoords.xy).r;

	out_Color = vec4(pass_color, 1.0f);
	
	out_Color = mix(out_Color, nightColor, dayValue);
	
	if(pass_id == selected) {
		out_Color *= vec4(shade, shade, shade, 1);
	}
	
	//if(shadowCoords.z > objectNearestLight) {
	//	out_Color = vec4(1,1,1,1);
	//}
	
}
