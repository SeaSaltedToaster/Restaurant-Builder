#version 400

const float shade = 1.5;

in vec3 pass_color;
in float pass_id;

out vec4 out_Color;

uniform float selected;

void main(void)	{

	out_Color = vec4(pass_color, 1.0f);
	
	if(pass_id == selected) {
		out_Color *= vec4(shade, shade, shade, 1);
	}

}
