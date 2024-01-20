#version 330 core
layout (location = 0) in vec2 v_pos;
layout (location = 1) in vec2 texCoord;

uniform int OBJECT;
uniform int POSITION_X;
uniform int POSITION_Y;

uniform float LETTER_POSITION;
uniform int LETTER;


out vec2 v_TexCoord;

void main(){
    if (OBJECT == 0 || OBJECT == 2 || OBJECT == 5){
        gl_Position = vec4(v_pos, 0.0, 1.0);
        v_TexCoord = vec2(texCoord.x, texCoord.y);
    }
    if (OBJECT == 1){
        gl_Position = vec4(
        v_pos.x - 0.3705 + 0.106 * POSITION_X,
        v_pos.y + 0.63 - 0.18 * POSITION_Y,
        0.0, 1.0);
        v_TexCoord = vec2(texCoord.x, texCoord.y);
    }
    if (OBJECT == 4){ // TEXT
        v_TexCoord = vec2(texCoord.x / 30 + 0.005 + 0.02662 * LETTER, texCoord.y);
        gl_Position = vec4(
        v_pos.x + (LETTER_POSITION * 0.7) - float(POSITION_X)/20,
        v_pos.y - float(POSITION_Y)/10,
        0.0, 1.0);
    }
}