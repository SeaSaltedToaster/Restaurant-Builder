
in vec3 pass_color;

out vec4 out_Color;

uniform vec3 primaryColor;
uniform vec3 secondaryColor;

void main(void)	{

	vec4 baseColor = vec4(pass_color, 1.0f);
	
	if(baseColor.x > 1.0 && baseColor.x <= 2.0) {
		float offset = baseColor.z;
		baseColor = vec4(primaryColor + vec3(offset), 1.0f);
	}
	
	if(baseColor.x > 2.0) {
		float offset = baseColor.z;
		baseColor = vec4(secondaryColor + vec3(offset), 1.0f);
	}

	out_Color = baseColor;
}
