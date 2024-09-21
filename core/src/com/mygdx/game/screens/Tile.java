package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class Tile {

    TmxMapLoader tmxMapLoader;
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer renderer;

    public Tile(String level){
        tmxMapLoader = new TmxMapLoader();
        tiledMap = tmxMapLoader.load(level);
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    public void render(OrthographicCamera camera){
        renderer.setView(camera);
        renderer.render();
    }

    public MapObjects loadMapObjects(String layerName){
        return tiledMap.getLayers().get(layerName).getObjects();
    }

    public MapObjects loadMapObjects(int index){
        return tiledMap.getLayers().get(index).getObjects();
    }

    public void createBodies(MapObjects mapObjects, World world, boolean isSensor){
        for (MapObject mapObject : mapObjects){
           if (mapObject instanceof RectangleMapObject){
               BodyDef bodyDef = new BodyDef();
               bodyDef.active = true;
               bodyDef.type = BodyDef.BodyType.StaticBody;
               Body body = world.createBody(bodyDef);
               PolygonShape shape = createPolygonShape((RectangleMapObject) mapObject);
               Fixture f = body.createFixture(shape, 1f);
               f.setFriction(0f);
               f.setSensor(isSensor);
           }

        }
    }

    private PolygonShape createPolygonShape(RectangleMapObject mapObject){
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(mapObject.getRectangle().getWidth()/2f, mapObject.getRectangle().getHeight()/2f,
                new Vector2(mapObject.getRectangle().getX() + mapObject.getRectangle().getWidth()/2f,
                        mapObject.getRectangle().getY() + mapObject.getRectangle().getHeight()/2f),0f);
        return polygonShape;
    }

    public ArrayList<RectangleMapObject> getRectanglesMapObjects(MapObjects mapObjects){
        ArrayList<RectangleMapObject> a = new ArrayList<>();
        for (MapObject m : mapObjects)
            a.add((RectangleMapObject) m);
        return a;
    }

}
