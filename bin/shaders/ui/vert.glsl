
in vec2 in_position;

out vec2 textureCoords;

uniform vec2 position;
uniform vec2 scale;

void main(void) {

	gl_Position = vec4(position + (scale * in_position), 0.0, 1.0);
	textureCoords = vec2((in_position.x+1.0)/2.0, 1 - (in_position.y+1.0)/2.0);

}
