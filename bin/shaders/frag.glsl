
in vec3 pass_color;
in vec3 surfaceNormal;

out vec4 out_Color;

void main(void)	{

	out_Color = vec4(pass_color, 1.0f);
	
	float normalSnow = dot(surfaceNormal, vec3(0,1,0));
    vec3 snowCol = vec3(1,1,1);
    vec3 snow = normalSnow * snowCol;
    
    bool isSnow = false;
    if(abs(normalSnow) > 0.5 && isSnow)
    	out_Color = vec4(snowCol, 1.0);

}
