
in vec3 in_position;

uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main(void){


	vec4 worldPosition = transformationMatrix * vec4(in_position, 1.0f);
	vec4 toCamPos = worldPosition * viewMatrix;
	gl_Position = toCamPos * projectionMatrix;
	
}