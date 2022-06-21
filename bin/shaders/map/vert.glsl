#version 400 core

//Per vertex variables
in vec3 position;
in vec3 color;
in vec3 normal;

//Pass to frag shader
out vec3 vertColor;

//Transforms
uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main(void) {

	//Transform
	vec4 worldPosition = transformationMatrix * vec4(position, 1.0f);
	vec4 positionRelativeToCamera = viewMatrix * worldPosition;
	gl_Position =  projectionMatrix * positionRelativeToCamera;

	//Out
	vertColor = color;

}
