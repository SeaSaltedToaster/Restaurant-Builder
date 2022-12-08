
const float lineHeight = 0.0f;

in vec2 position;
in vec2 textureCoords;

out vec2 pass_textureCoords;

uniform vec2 translation;
uniform float scale;

void main(void){

	float downTranslation = (1.0 - 0.025f / 2);
	vec2 textPosition = vec2(position.x, position.y - downTranslation);
	textPosition *= scale;

	gl_Position = vec4(textPosition + translation, 0.0f, 1.0f);
	pass_textureCoords = textureCoords;

}
