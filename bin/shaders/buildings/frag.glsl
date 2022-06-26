#version 400

in flat vec3 pass_color;

uniform bool isPreview;

out vec4 out_Color;

uniform int currentId;
uniform int selectedId;

void main(void)	{

	out_Color = vec4(pass_color, 1.0f);

	if(currentId == selectedId) {
		out_Color *= 2;
	}

}
