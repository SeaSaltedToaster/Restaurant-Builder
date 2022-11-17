#version 400

const float shade = 2.0;
const vec4 nightColor = vec4(0.0f);

in vec3 pass_color;
flat in float pass_id;

in vec4 shadowCoords;

out vec4 out_Color;

uniform sampler2D shadowMap;

uniform int selected;
uniform float dayValue;

void main(void)	{

	vec4 baseColor = vec4(pass_color, 1.0f);	
	vec4 litColor = mix(baseColor, nightColor, dayValue);
	
	int fullId = int(pass_id + 0.5f);
	if(fullId == selected) {
		out_Color = litColor * vec4(vec3(shade), 1.0f);
	}
	else {
		out_Color = litColor;
	}
	
}
