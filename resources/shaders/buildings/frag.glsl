
const vec4 nightColor = vec4(0.0f);

const vec3 primaryInd = vec3(0.1f, 0.15f, 0.2f);
const vec3 secondaryInd = vec3(0.2f, 0.15f, 0.1f);
const float factor = 0.25;

in vec3 pass_color;
in vec3 surfaceNormal;

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
		float x = (litColor.x > 0.5) ? litColor.x - factor : litColor.x + factor;
		float y = (litColor.y > 0.5) ? litColor.y - factor : litColor.y + factor;
		float z = (litColor.z > 0.5) ? litColor.z - factor : litColor.z + factor;
		litColor = vec4(x, y, z, 1);
	}
	
	out_Color = litColor;
	
	bool snow = false;
	if(snow)
	{
		float normalSnow = dot(surfaceNormal, vec3(0,1,0));
		normalSnow + 0.5;
			
	    vec3 snowCol = vec3(1.0);
	    vec3 snow = normalSnow * snowCol;
	    if(normalSnow > 0.75)
	    	out_Color = vec4(snow, 1);
    }

}
