
in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D guiTexture;
uniform vec3 color;
uniform float alpha;
uniform float uiWidth;
uniform float uiHeight;
uniform bool hasTexture;

void main(void){

	vec4 textureColor = texture(guiTexture,textureCoords);	
	out_Color = textureColor;
	
	out_Color += vec4(color,0);
	
	if(alpha != 1.0f)
		out_Color.a = alpha;
}
