
const vec4 nightColor = vec4(0.0f);

const vec3 primaryInd = vec3(0.1f, 0.15f, 0.2f);
const vec3 secondaryInd = vec3(0.2f, 0.15f, 0.1f);

in vec3 pass_color;

uniform bool isPreview;

out vec4 out_Color;

uniform int currentId;
uniform int selectedId;
uniform float dayValue;

uniform vec3 primaryColor;
uniform vec3 secondaryColor;

void main(void)	{

	vec4 baseColor = vec4(pass_color, 1.0f);
	
	if(baseColor.x > 1.0 && baseColor.x <= 2.0) {
		float offset = baseColor.z;
		baseColor = vec4(primaryColor + vec3(offset), 1.0f);
	}
	
	if(baseColor.x > 2.0) {
		float offset = baseColor.z;
		baseColor = vec4(secondaryColor + vec3(offset), 1.0f);
	}
		
	vec4 litColor = mix(baseColor, nightColor, dayValue);
	
	if(currentId == selectedId || currentId == -1) {
		//litColor = vec4(1,1,1,1);
	}
	
	out_Color = litColor;

}
