
in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D colorTexture;

uniform float brightness;
uniform float contrast;

void main(void){

	//FBO texture
	out_Color = texture(colorTexture, textureCoords);

	//Contrast effect
	out_Color.rgb = (out_Color.rgb - 0.5f) * (1.0f + contrast) + 0.5f;


	//Brightness effect
	out_Color = out_Color * (0.0f + brightness);

}
