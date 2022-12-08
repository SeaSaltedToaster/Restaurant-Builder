
in vec4 out_position;
out vec4 out_Color;

uniform float skyboxSize;
uniform float dayValue;

float levels = 8.0;

float remap( float minval, float maxval, float curval )
{
    return ( curval - minval ) / ( maxval - minval );
}

void main(void){

	out_Color = vec4(0,1,0,1);
	vec2 uv = vec2(out_position.x,(out_position.y/(skyboxSize)));

	float pi = 3.141592653589793;

	//Day Time
	vec4 c1 = vec4(1.0, 1.0, 1.0, 0.0); //Top Color
	vec4 c2 = vec4(1.0, 1.0, 1.0, 0.5); //Horizon Color
	vec4 c3 = vec4(0.0, 0.7, 1.0, 0.0); //Bottom Color

	//Night Time
	vec4 nightColor = vec4(0.0f); //Top Color

	float f = 1.0-acos(uv.y)/pi;

	float top = 1.0f;
	float middle = 0.25f;
	float bottom = 0.0f;

	if(f < bottom){
		vec4 colD = mix(c1,c2,uv.y);
		out_Color = colD;
	}
	else if(f >= bottom && f < middle){
	    out_Color = c2;
	}
	else if(f >= middle){
		vec4 colD = mix(c2, c3, (f-middle)/0.5f);
		out_Color = colD;
	}
	out_Color = mix(out_Color, nightColor, dayValue);

 	//USED FOR SIDE BY SIDE COMPARISON (Old vs New Sky)
 	//float comparisonX = 20.0f;
	//if(uv.x > comparisonX)
		//out_Color = vec4(0.0,0.5,1.0,1.0);

}
