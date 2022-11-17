#version 400

in vec3 position;
in vec3 color;
in vec3 normal;
in int id;

out vec3 pass_color;
out float pass_id;

out vec4 shadowCoords;
uniform mat4 toShadowMapSpace;

uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main(void)	{

	vec4 worldPosition = transformationMatrix * vec4(position, 1.0f);
	shadowCoords = toShadowMapSpace * worldPosition;
	
	vec4 toCamPos = worldPosition * viewMatrix;
	gl_Position = toCamPos * projectionMatrix;
	
	pass_color = color;
	pass_id = id;
}
