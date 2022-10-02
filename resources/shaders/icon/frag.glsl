#version 400

in flat vec3 pass_color;

out vec4 out_Color;

uniform vec3 primaryColor;
uniform vec3 secondaryColor;

void main(void)	{

	out_Color = vec4(pass_color, 1.0f);
	
	if(out_Color.x == 2) {
		float offset = out_Color.z;
		out_Color = vec4(primaryColor + vec3(offset), 1.0f);
	}
	
	if(out_Color.x == 3) {
		float offset = out_Color.z;
		out_Color = vec4(secondaryColor + vec3(offset), 1.0f);
	}

}
