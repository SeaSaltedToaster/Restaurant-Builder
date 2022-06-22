#version 400

in flat vec3 pass_color;

out vec4 out_Color;

void main(void)	{

	out_Color = vec4(pass_color, 1.0f);

}
