
in vec3 in_position;

uniform mat4 transformationMatrix;
uniform mat4 lightViewMatrix;

void main(void){

	vec4 worldPosition = transformationMatrix * vec4(in_position, 1.0f);
	gl_Position = worldPosition * lightViewMatrix;
	
}