
const float shade = 5.0;
const vec4 nightColor = vec4(0.0f);

in vec3 pass_color;
in float pass_id;
in vec3 surfaceNormal;

in vec4 shadowCoords;

out vec4 out_Color;

uniform sampler2D shadowMap;

uniform int selected;
uniform float dayValue;

void main(void)	{

	float objectNearestLight = texture(shadowMap, shadowCoords.xy).r;
	
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
