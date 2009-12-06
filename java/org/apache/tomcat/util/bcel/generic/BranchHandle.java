/*
 * Copyright  2000-2009 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License. 
 *
 */
package org.apache.tomcat.util.bcel.generic;

/**
 * BranchHandle is returned by specialized InstructionList.append() whenever a
 * BranchInstruction is appended. This is useful when the target of this
 * instruction is not known at time of creation and must be set later
 * via setTarget().
 *
 * @see InstructionHandle
 * @see Instruction
 * @see InstructionList
 * @version $Id$
 * @author  <A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>
 */
public final class BranchHandle extends InstructionHandle {

    private BranchInstruction bi; // An alias in fact, but saves lots of casts


    private BranchHandle(BranchInstruction i) {
        super(i);
        bi = i;
    }

    /** Factory methods.
     */
    private static BranchHandle bh_list = null; // List of reusable handles


    


    /** Handle adds itself to the list of resuable handles.
     */
    protected void addHandle() {
        next = bh_list;
        bh_list = this;
    }


    /* Override InstructionHandle methods: delegate to branch instruction.
     * Through this overriding all access to the private i_position field should
     * be prevented.
     */
    public int getPosition() {
        return bi.position;
    }


    void setPosition( int pos ) {
        i_position = bi.position = pos;
    }


    protected int updatePosition( int offset, int max_offset ) {
        int x = bi.updatePosition(offset, max_offset);
        i_position = bi.position;
        return x;
    }


    


    


    


    /** 
     * Set new contents. Old instruction is disposed and may not be used anymore.
     */
    public void setInstruction( Instruction i ) {
        super.setInstruction(i);
        if (!(i instanceof BranchInstruction)) {
            throw new ClassGenException("Assigning " + i
                    + " to branch handle which is not a branch instruction");
        }
        bi = (BranchInstruction) i;
    }
}
