/*
 * Copyright (c) [2016-2017] [University of Minnesota]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.grouplens.samantha.modeler.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public class CatDAO implements EntityDAO {
    private final List<EntityDAO> entityDAOList;
    private int curIdx = 0;

    public CatDAO(List<EntityDAO> entityDAOList) {
        this.entityDAOList = entityDAOList;
    }

    public boolean hasNextEntity() {
        if (curIdx >= entityDAOList.size()) {
            return false;
        }
        while (curIdx < entityDAOList.size() && !entityDAOList.get(curIdx).hasNextEntity()) {
            curIdx++;
        }
        return entityDAOList.get(curIdx).hasNextEntity();
    }

    public ObjectNode getNextEntity() {
        return entityDAOList.get(curIdx).getNextEntity();
    }

    public void restart() {
        for (EntityDAO entityDAO : entityDAOList) {
            entityDAO.restart();
        }
        curIdx = 0;
    }

    public void close() {
        for (EntityDAO entityDAO : entityDAOList) {
            entityDAO.close();
        }
        curIdx = entityDAOList.size();
    }
}