#version 150

const float lineHeight = 0.03f;

in vec2 position;
in vec2 textureCoords;

out vec2 pass_textureCoords;

uniform vec2 translation;
uniform vec2 bounds;

void main(void){

	float downTranslation = (1.0 - bounds.y / 2) - lineHeight;
	vec2 textTranslation = vec2(translation.x, translation.y - downTranslation);
	vec2 textPosition = vec2(position.x, position.y);

	gl_Position = vec4( textPosition + textTranslation, 0.0, 1.0);
	pass_textureCoords = textureCoords;

}
