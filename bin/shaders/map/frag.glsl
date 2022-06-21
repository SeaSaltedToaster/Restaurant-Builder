#version 400 core

//Per vertex variables
in vec3 vertColor;

//Out
out vec4 outColor;

void main(void) {

	outColor = vec4(vertColor, 1.0);

}
