package com.austinlaimos.camelmobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Enemy extends Object {

    Rect rect;
    Paint paint;

    Rect healthRect;
    Paint healthPaint;

    Rect healthBarRect;
    Paint healthBarPaint;

    float timer = 0;

    int tileIndex = 0;

    boolean finished = false;
    float speed = 1f;

    float health;

    float maxHealth;

    int enemyIndex;

    public Enemy(Enemy enemy){
        point = new Point(enemy.point);
        rect = new Rect(enemy.rect);
        paint = new Paint(enemy.paint);
        rect.set(point.x - rect.width() / 2, point.y - rect.height() / 2, point.x + rect.width() / 2, point.y + rect.height() / 2);
        this.health = enemy.health;
        this.speed = enemy.speed;
        this.health = enemy.health;
        this.healthRect = new Rect(enemy.rect);
        this.healthPaint = new Paint(enemy.healthPaint);
        this.healthBarRect = new Rect(enemy.healthBarRect);
        this.healthBarPaint = new Paint(enemy.healthBarPaint);
        this.maxHealth = enemy.maxHealth;
        this.enemyIndex = enemy.enemyIndex;
    }

    public Enemy(Rect rectangle, Point point, int color, float health, int enemyIndex){
        rect = rectangle;
        paint = new Paint();
        paint.setColor(color);
        this.point = point;
        rect.set(point.x - rect.width() / 2, point.y - rect.height() / 2, point.x + rect.width() / 2, point.y + rect.height() / 2);
        this.health = health;
        healthPaint = new Paint();
        healthPaint.setColor(Color.rgb(255, 0, 0));
        healthBarPaint = new Paint();
        healthBarPaint.setColor(Color.rgb(0, 0, 0));
        healthRect = new Rect();
        healthBarRect = new Rect();
        maxHealth = health;

        this.enemyIndex = enemyIndex;

        healthRect.set(point.x + rect.width() + 10, point.y - rect.height() / 2, (int)(point.x + rect.width() / 1.5f) + 10, point.y + rect.height() / 2);
        healthBarRect.set(point.x + rect.width() + 10, point.y - rect.height() / 2, (int)(point.x + rect.width() / 1.5f) + 10, point.y + rect.height() / 2);
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawRect(rect, paint);
        canvas.drawRect(healthBarRect, healthBarPaint);
        canvas.drawRect(healthRect, healthPaint);
    }

    @Override
    public void update(long deltaTime){
        if(!finished){
            //Advance on the tiles
            Tile tile = ((LevelScene) Game.instance.getCurScene()).tiles.get(tileIndex);

            if(tile.dir == Tile.Direction.down){
                translate(point.x, (int)lerp(tile.rect.top, tile.rect.bottom - tile.rect.width() / 2, timer / (1000 * speed)));
            }
            else if(tile.dir == Tile.Direction.up){
                translate(point.x, (int)lerp(tile.rect.bottom, tile.rect.top + tile.rect.width() / 2, timer / (1000 * speed)));
            }
            else if(tile.dir == Tile.Direction.left){
                translate((int)lerp(tile.rect.right, tile.rect.left + tile.rect.height() / 2, timer / (1000 * speed)), point.y);
            }
            else if(tile.dir == Tile.Direction.right){
                translate((int)lerp(tile.rect.left, tile.rect.right - tile.rect.height() / 2, timer / (1000 * speed)), point.y);
            }

            timer += deltaTime;

            if(timer >= 1000 * speed){
                //Advance to the next tile
                tileIndex++;
                timer = 0;

                if(tileIndex >= ((LevelScene) Game.instance.getCurScene()).tiles.size()){
                    //The enemy made it to the end
                    finished = true;
                    ((LevelScene) Game.instance.getCurScene()).enemyEnd(this);
                    return;
                }

                tile = ((LevelScene) Game.instance.getCurScene()).tiles.get(tileIndex);

                if(tile.dir == Tile.Direction.down){
                    translate(tile.rect.centerX(), tile.rect.top);
                    speed = tile.rect.height() / 100;
                }
                else if(tile.dir == Tile.Direction.up){
                    translate(tile.rect.centerX(), tile.rect.bottom);
                    speed = tile.rect.height() / 100;
                }
                else if(tile.dir == Tile.Direction.left){
                    translate(tile.rect.right, tile.rect.centerY());
                    speed = tile.rect.width() / 100;
                }
                else if(tile.dir == Tile.Direction.right){
                    translate(tile.rect.left, tile.rect.centerY());
                    speed = tile.rect.width() / 100;
                }
            }
        }
    }

    @Override
    public void onTap(int x, int y){

    }

    public void tickDamage(float damage){
        health -= damage;
        healthRect.set(point.x - rect.width() / 2, point.y - rect.height() - 10, (int)(point.x + rect.width() / 1.5f) + 10, (point.y + rect.height() / 2) - (int)((1 - health / maxHealth) * rect.height()));
        if(health <= 0){
            ((LevelScene) Game.instance.getCurScene()).enemies.remove(this);
            if(enemyIndex > 0){
                ((LevelScene) Game.instance.getCurScene()).SpawnEnemy(enemyIndex - 1, tileIndex, timer, point);
            }
        }
    }

    public void translate(int x, int y){
        this.point.x = x;
        this.point.y = y;
        rect.set(point.x - rect.width() / 2, point.y - rect.height() / 2, point.x + rect.width() / 2, point.y + rect.height() / 2);
        healthRect.set(point.x + rect.width() + 10, point.y - rect.height() / 2, (int)(point.x + rect.width() / 1.5f) + 10, (point.y + rect.height() / 2) - (int)((1 - health / maxHealth) * rect.height()));
        healthBarRect.set(point.x + rect.width() + 10, point.y - rect.height() / 2, (int)(point.x + rect.width() / 1.5f) + 10, point.y + rect.height() / 2);
    }

    public float lerp(float a, float b, float time){
        return a + time * (b - a);
    }

}
