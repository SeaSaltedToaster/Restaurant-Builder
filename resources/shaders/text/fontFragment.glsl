
in vec2 pass_textureCoords;

out vec4 out_color;

uniform vec3 color;
uniform sampler2D fontAtlas;
uniform float alpha;

void main(void){

	vec4 texColor = vec4(color, 1.0);
	texColor.a = alpha;

	out_color = vec4(color, texture(fontAtlas, pass_textureCoords).a - (1.0 - alpha) );

}
