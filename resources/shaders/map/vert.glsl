
in vec3 position;
in vec3 color;
in vec3 normal;
in int id;

out vec3 pass_color;
out float pass_id;
out vec3 surfaceNormal;

out vec4 shadowCoords;

uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform mat4 lightViewMatrix;

void main(void)	{

	vec4 worldPosition = transformationMatrix * vec4(position, 1.0f);
	gl_Position = lightViewMatrix * worldPosition;
				
	vec4 toCamPos = worldPosition * viewMatrix;
	gl_Position = toCamPos * projectionMatrix;
			
	surfaceNormal = (transformationMatrix * vec4(normal,0.0)).xyz;
			
	pass_color = color;
	pass_id = float(id);
}
